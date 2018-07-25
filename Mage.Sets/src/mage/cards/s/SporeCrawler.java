package mage.cards.s;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class SporeCrawler extends CardImpl {

    public SporeCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Spore Crawler enters the battlefield tapped.
        // {T}: Add {G} to your mana pool.
        // {1}{G}: Spore Crawler becomes a 1/3 green Zerg creature with reach until end of turn. It's still a land.
    }

    public SporeCrawler(final SporeCrawler card) {
        super(card);
    }

    @Override
    public SporeCrawler copy() {
        return new SporeCrawler(this);
    }
}
