package mage.cards.g;

import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.hint.common.NotMyTurnHint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class GhostTown extends CardImpl {

    public GhostTown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {0}: Return Ghost Town to its owner's hand. Activate this ability only if it's not your turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true),
                new GenericManaCost(0), NotMyTurnCondition.instance)
                .addHint(NotMyTurnHint.instance));
    }

    private GhostTown(final GhostTown card) {
        super(card);
    }

    @Override
    public GhostTown copy() {
        return new GhostTown(this);
    }
}
