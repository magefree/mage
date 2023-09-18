package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author jonubuu
 */
public final class Electrolyze extends CardImpl {

    public Electrolyze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{R}");

        // Electrolyze deals 2 damage divided as you choose among one or two targets.
        this.getSpellAbility().addEffect(new DamageMultiEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(2));
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Electrolyze(final Electrolyze card) {
        super(card);
    }

    @Override
    public Electrolyze copy() {
        return new Electrolyze(this);
    }
}
