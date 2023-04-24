package mage.cards.z;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZhalfirinShapecraft extends CardImpl {

    public ZhalfirinShapecraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Target creature has base power and toughness 4/3 until end of turn.
        this.getSpellAbility().addEffect(new SetBasePowerToughnessTargetEffect(4, 3, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ZhalfirinShapecraft(final ZhalfirinShapecraft card) {
        super(card);
    }

    @Override
    public ZhalfirinShapecraft copy() {
        return new ZhalfirinShapecraft(this);
    }
}
