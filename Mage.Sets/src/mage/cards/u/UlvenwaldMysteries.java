package mage.cards.u;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class UlvenwaldMysteries extends CardImpl {

    private static final FilterControlledPermanent filterClue = new FilterControlledPermanent("a Clue");

    static {
        filterClue.add(SubType.CLUE.getPredicate());
    }

    public UlvenwaldMysteries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // Whenever a nontoken creature you control dies, investigate. <i>(Create a colorless Clue artifact token with "{2}, Sacrifice this artifact: Draw a card.")</i>
        this.addAbility(new DiesCreatureTriggeredAbility(new InvestigateEffect(), false, StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN));

        // Whenever you sacrifice a Clue, create a 1/1 white Human Soldier creature token.
        this.addAbility(new SacrificePermanentTriggeredAbility(new CreateTokenEffect(new HumanSoldierToken()), filterClue));
    }

    private UlvenwaldMysteries(final UlvenwaldMysteries card) {
        super(card);
    }

    @Override
    public UlvenwaldMysteries copy() {
        return new UlvenwaldMysteries(this);
    }
}
