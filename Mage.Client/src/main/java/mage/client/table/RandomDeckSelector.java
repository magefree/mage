package mage.client.table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Resolves AI deck inputs. A regular file is used directly; a directory means
 * choose one .dck file from that directory tree.
 */
public final class RandomDeckSelector {

    /**
     * Prevents instantiation of this utility class.
     */
    private RandomDeckSelector() {
    }

    /**
     * Resolve a deck path: if the argument names a single deck file the same path is returned;
     * if it names a directory a single `.dck` file is selected and its path is returned;
     * if the argument is `null` or blank it is returned unchanged.
     *
     * @param deckPath path to a deck file or a directory containing `.dck` files; may be null or blank
     * @return the resolved deck file path (or the original `deckPath` if null/blank or already a file)
     * @throws IOException if filesystem access (walking the directory or resolving canonical paths) fails
     */
    public static String resolveDeckPath(String deckPath) throws IOException {
        return resolveDeckPath(deckPath, new HashSet<>());
    }

    /**
     * Resolve a deck path or, when given a directory, select a random `.dck` file from that directory tree and record it.
     *
     * <p>If {@code deckPath} is null or blank, it is returned unchanged. If it points to a file, that path is recorded in
     * {@code usedDeckPaths} and returned. If it points to a directory, the directory is scanned for regular files ending
     * with {@code .dck} (case-insensitive); one file is chosen at random (preferring files not already present in
     * {@code usedDeckPaths}), recorded in {@code usedDeckPaths}, and its path string is returned.</p>
     *
     * @param deckPath       path to a deck file or a directory containing `.dck` files; may be null or blank
     * @param usedDeckPaths  mutable set used to track canonical, normalized deck paths that have already been used
     * @return the original {@code deckPath} when it refers to a file or is blank/null, or the path string of the selected `.dck` file when a directory is provided
     * @throws IOException if an I/O error occurs while scanning or resolving file canonical paths, or if no `.dck` files are found in the provided directory
     */
    public static String resolveDeckPath(String deckPath, Set<String> usedDeckPaths) throws IOException {
        if (deckPath == null || deckPath.trim().isEmpty()) {
            return deckPath;
        }

        File deckFile = new File(deckPath);
        if (!deckFile.isDirectory()) {
            rememberDeck(deckFile, usedDeckPaths);
            return deckPath;
        }

        List<Path> deckFiles;
        try (Stream<Path> paths = Files.walk(deckFile.toPath())) {
            deckFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(RandomDeckSelector::isDeckFile)
                    .sorted()
                    .collect(Collectors.toList());
        }

        if (deckFiles.isEmpty()) {
            throw new FileNotFoundException("No .dck files found in random AI deck folder: " + deckFile.getPath());
        }

        List<Path> availableDeckFiles = new ArrayList<>();
        for (Path path : deckFiles) {
            if (!usedDeckPaths.contains(toDeckKey(path.toFile()))) {
                availableDeckFiles.add(path);
            }
        }

        List<Path> selectableDeckFiles = availableDeckFiles.isEmpty() ? deckFiles : availableDeckFiles;
        Path selectedDeck = selectableDeckFiles.get(ThreadLocalRandom.current().nextInt(selectableDeckFiles.size()));
        rememberDeck(selectedDeck.toFile(), usedDeckPaths);
        return selectedDeck.toString();
    }

    /**
     * Checks whether the filename of the given path uses the `.dck` extension.
     *
     * @param path the path whose filename will be checked
     * @return {@code true} if the filename ends with `.dck` (case-insensitive), {@code false} otherwise
     */
    private static boolean isDeckFile(Path path) {
        String filename = path.getFileName().toString().toLowerCase(Locale.ENGLISH);
        return filename.endsWith(".dck");
    }

    /**
     * Record the given deck file in the provided set using its canonical key.
     *
     * Adds the file's canonical path converted to lowercase (the deck key) to {@code usedDeckPaths}.
     *
     * @param deckFile the deck file to record
     * @param usedDeckPaths the set to which the deck's canonical key will be added
     * @throws IOException if the file's canonical path cannot be resolved
     */
    private static void rememberDeck(File deckFile, Set<String> usedDeckPaths) throws IOException {
        usedDeckPaths.add(toDeckKey(deckFile));
    }

    /**
     * Produce a stable, filesystem-normalized key for a deck file based on its canonical path.
     *
     * The key is the file's canonical path converted to lowercase using Locale.ENGLISH.
     *
     * @param deckFile the deck file to convert into a normalized key
     * @return the normalized canonical path string to use as a key
     * @throws IOException if the file's canonical path cannot be resolved
     */
    private static String toDeckKey(File deckFile) throws IOException {
        return deckFile.getCanonicalPath().toLowerCase(Locale.ENGLISH);
    }
}
