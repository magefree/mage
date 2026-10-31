package mage.server.draft;

import mage.cards.Card;
import mage.game.draft.DraftPickSuggest;
import mage.game.draft.DraftPickSuggester;
import mage.util.ThreadUtils;
import mage.util.XmageThreadFactory;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Draft: all sources of suggested picks for human players
 * <p>
 * How to add a new source: implement DraftPickSuggester and call DraftPickSuggesters.register on server's start
 *
 * @author JayDi85
 */
public final class DraftPickSuggesters {

    private static final Logger logger = Logger.getLogger(DraftPickSuggesters.class);

    // suggestions calculated before booster send to a human (only first send of each pick),
    // so a slow source delays the booster - a late answer is ignored
    public static final int SUGGEST_TIMEOUT_MS = 1000;
    private static final int MAX_THREADS = 32; // all drafts on the server, a source without free thread will be skipped

    private static final List<DraftPickSuggester> suggesters = new CopyOnWriteArrayList<>(
            Collections.singletonList(new RatingDraftPickSuggester())
    );

    private static final ExecutorService executor = new ThreadPoolExecutor(
            0, MAX_THREADS, 60, TimeUnit.SECONDS, new SynchronousQueue<>(),
            new XmageThreadFactory(ThreadUtils.THREAD_PREFIX_TOURNEY_PICK_SUGGEST)
    );

    private DraftPickSuggesters() {
    }

    public static void register(DraftPickSuggester suggester) {
        suggesters.add(suggester);
    }

    public static List<DraftPickSuggester> getAll() {
        return new ArrayList<>(suggesters);
    }

    public static List<DraftPickSuggest> makeSuggests(List<Card> booster, List<Card> picks) {
        return makeSuggests(booster, picks, getAll(), SUGGEST_TIMEOUT_MS);
    }

    /**
     * Suggestions of all sources in sources order (each source's list in its own order)
     */
    public static List<DraftPickSuggest> makeSuggests(List<Card> booster, List<Card> picks,
                                                      List<DraftPickSuggester> sources, int timeoutMs) {
        // run all sources at the same time, each source gets own copies of the cards (thread safe and can't change the draft)
        Set<UUID> boosterIds = booster.stream().map(Card::getId).collect(Collectors.toSet());
        List<Future<List<DraftPickSuggest>>> tasks = new ArrayList<>();
        for (DraftPickSuggester source : sources) {
            List<Card> boosterCopy = copyCards(booster);
            List<Card> picksCopy = copyCards(picks);
            Future<List<DraftPickSuggest>> task = null;
            try {
                task = executor.submit(() -> source.suggest(boosterCopy, picksCopy));
            } catch (RejectedExecutionException e) {
                logger.warn("Draft pick suggest: no free thread for " + source.getName() + ", skipped");
            }
            tasks.add(task);
        }

        long deadline = System.currentTimeMillis() + timeoutMs;
        List<DraftPickSuggest> res = new ArrayList<>();
        for (int i = 0; i < sources.size(); i++) {
            DraftPickSuggester source = sources.get(i);
            Future<List<DraftPickSuggest>> task = tasks.get(i);
            if (task == null) {
                continue;
            }
            List<DraftPickSuggest> suggests;
            try {
                suggests = task.get(Math.max(0, deadline - System.currentTimeMillis()), TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                task.cancel(true);
                logger.warn("Draft pick suggest: " + source.getName() + " is too slow (more than " + timeoutMs + " ms), skipped");
                continue;
            } catch (InterruptedException e) {
                // server's shutdown or draft's end
                tasks.stream().filter(Objects::nonNull).forEach(t -> t.cancel(true));
                Thread.currentThread().interrupt();
                break;
            } catch (ExecutionException e) {
                logger.error("Draft pick suggest: " + source.getName() + " failed", e.getCause());
                continue;
            }
            if (suggests == null) {
                continue;
            }
            int rank = 0;
            for (DraftPickSuggest suggest : suggests) {
                if (suggest != null && boosterIds.contains(suggest.getCardId())) {
                    rank++;
                    res.add(suggest.withSource(source.getName(), rank));
                }
            }
        }
        return res;
    }

    private static List<Card> copyCards(List<Card> cards) {
        return cards.stream().map(Card::copy).collect(Collectors.toList());
    }
}
