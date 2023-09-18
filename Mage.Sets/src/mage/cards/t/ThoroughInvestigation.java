package mage.cards.t;

import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThoroughInvestigation extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.CLUE, "a Clue");

    public ThoroughInvestigation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever you attack, investigate.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new InvestigateEffect(), 1));

        // Whenever you sacrifice a Clue, venture into the dungeon.
        this.addAbility(new SacrificePermanentTriggeredAbility(new VentureIntoTheDungeonEffect(), filter));
    }

    private ThoroughInvestigation(final ThoroughInvestigation card) {
        super(card);
    }

    @Override
    public ThoroughInvestigation copy() {
        return new ThoroughInvestigation(this);
    }
}
