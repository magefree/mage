package mage.cards.w;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WitchsMark extends CardImpl {

    public WitchsMark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // You may discard a card. If you do, draw two cards.
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(2),
                new DiscardCardCost()
        ));

        // Create a Wicked Role token attached to up to one target creature you control.
        this.getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.WICKED).concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 1));
    }

    private WitchsMark(final WitchsMark card) {
        super(card);
    }

    @Override
    public WitchsMark copy() {
        return new WitchsMark(this);
    }
}
