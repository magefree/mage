package mage.server.bdd;

import org.jbehave.core.io.StoryFinder;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Runs Mage stories (tests)
 *
 * @author nantuko
 */
public class MageStoryRunner {
    public static void main(String[] argv) throws Throwable {
        MageEmbedder embedder = new MageEmbedder();
        List<String> storyPaths = (new StoryFinder()).findPaths("src/test/java", asList("stories/land.story"), null);
        System.out.println("Found stories count: " + storyPaths.size());
        embedder.runStoriesAsPaths(storyPaths);
    }
}
