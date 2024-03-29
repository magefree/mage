package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VisageBandit extends CardImpl {

    public VisageBandit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // You may have Visage Bandit enter the battlefield as a copy of a creature you control, except it's a Shapeshifter Rogue in addition to its other types.
        this.addAbility(new EntersBattlefieldAbility(
                new CopyPermanentEffect(StaticFilters.FILTER_CONTROLLED_CREATURE, new VisageBanditCopyApplier()),
                true, null, "You may have {this} enter the battlefield as a copy of " +
                "a creature you control, except it's a Shapeshifter Rogue in addition to its other types.", ""
        ));

        // Plot {2}{U}
        this.addAbility(new PlotAbility("{2}{U}"));
    }

    private VisageBandit(final VisageBandit card) {
        super(card);
    }

    @Override
    public VisageBandit copy() {
        return new VisageBandit(this);
    }
}

class VisageBanditCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.addSubType(SubType.SHAPESHIFTER, SubType.ROGUE);
        return true;
    }
}
