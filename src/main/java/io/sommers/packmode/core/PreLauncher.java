package io.sommers.packmode.core;

import io.sommers.packmode.PMConfig;
import net.minecraftforge.fml.relauncher.IFMLCallHook;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.Map;

public class PreLauncher implements IFMLCallHook {

    private File mcLocation;
    private File cfgOverride;

    @Override
    public void injectData(Map<String, Object> data) {
        mcLocation = (File) data.get("mcLocation");
    }

    @Override
    public Void call() {
        if (!PMConfig.disabledTipScreen()) {
            JFrame frame = new JFrame("PackMode Selector");
            frame.setSize(350, 200);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Thread.currentThread().interrupt();
                }
            });

            JPanel panel = new JPanel();
            frame.add(panel);
            JComboBox<String> box = new JComboBox<>(PMConfig.getAcceptedModes());
            box.setBounds(130, 22, 100, 21);
            panel.add(box);
            JButton button = new JButton("Apply");
            button.addActionListener((e) -> {
                PMConfig.setPackMode((String) box.getSelectedItem());
                overrideConfig();
                PMConfig.setDisableTipScreen(true);
                frame.dispose();
            });
            panel.add(button);

            frame.setVisible(true);
        } else {
            overrideConfig();
        }
        return null;
    }

    private void overrideConfig() {
        File cfgCurrent = new File(mcLocation, "config");
        cfgOverride = new File(mcLocation, "config/packmode/" + PMConfig.getPackMode().toLowerCase());
        if (cfgOverride.isDirectory()) {
            Path sourceDir = Paths.get(cfgOverride.toURI());
            Path targetDir = Paths.get(cfgCurrent.toURI());

            try {
                if (!Files.exists(targetDir)) {
                    Files.createDirectories(targetDir);
                }

                Files.walkFileTree(sourceDir, new Impl(targetDir, sourceDir));

                Files.walk(sourceDir)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Impl extends SimpleFileVisitor<Path> {

        private final Path targetDir;
        private final Path sourceDir;

        private Impl(Path targetDir, Path sourceDir) {

            this.targetDir = targetDir;
            this.sourceDir = sourceDir;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Path targetFile = targetDir.resolve(sourceDir.relativize(file));
            Files.createDirectories(targetFile.getParent());
            Files.move(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            return FileVisitResult.CONTINUE;
        }
    }
}
