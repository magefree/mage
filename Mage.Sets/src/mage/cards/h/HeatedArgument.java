package mage.cards.h;

import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeatedArgument extends CardImpl {

    public HeatedArgument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Heated Argument deals 6 damage to target creature. You may exile a card from your graveyard. If you do, Heated Argument also deals 2 damage to that creature's controller.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new DamageTargetControllerEffect(2)
                        .setText("{this} also deals 2 damage to that creature's controller"),
                new ExileFromGraveCost(new TargetCardInYourGraveyard())
        ));
    }

    private HeatedArgument(final HeatedArgument card) {
        super(card);
    }

    @Override
    public HeatedArgument copy() {
        return new HeatedArgument(this);
    }
}
