package mage.cards.i;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IncineratingBlast extends CardImpl {

    public IncineratingBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Incinerating Blast deals 6 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // You may discard a card. If you do, draw a card.
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new DiscardCardCost()
        ).concatBy("<br>"));
    }

    private IncineratingBlast(final IncineratingBlast card) {
        super(card);
    }

    @Override
    public IncineratingBlast copy() {
        return new IncineratingBlast(this);
    }
}
