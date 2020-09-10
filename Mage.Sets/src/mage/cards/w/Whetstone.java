package mage.cards.w;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class Whetstone extends CardImpl {

    public Whetstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        //{3}: Each player puts the top two cards of their library into their graveyard.
        this.addAbility(new SimpleActivatedAbility(new MillCardsEachPlayerEffect(
                2, TargetController.ANY
        ), new GenericManaCost(3)));
    }

    private Whetstone(final Whetstone card) {
        super(card);
    }

    @Override
    public Whetstone copy() {
        return new Whetstone(this);
    }
}
