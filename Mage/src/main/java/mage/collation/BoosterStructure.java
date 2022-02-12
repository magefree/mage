package mage.collation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Current implementation is built only for sequential collation
 * Boosters are collated through a variety of different methods
 * Striped collation not supported yet
 * For more information: https://www.lethe.xyz/mtg/collation/
 *
 * @author TheElk801
 */
public class BoosterStructure {

    private final List<CardRun> slots;

    public BoosterStructure(CardRun... runs) {
        this.slots = Arrays.asList(runs);
    }

    public List<String> makeRun() {
        List<String> cards = new ArrayList<>();
        for (CardRun run : this.slots) {
            cards.add(run.getNext());
        }
        return cards;
    }
}
