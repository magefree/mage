package mage.cards.g;

import mage.abilities.condition.common.DrewTwoOrMoreCardsCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Galvanize extends CardImpl {

    public Galvanize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Galvanize deals 3 damage to target creature. If you've drawn two or more cards this turn, Galvanize deals 5 damage to that creature instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(5), new DamageTargetEffect(3),
                DrewTwoOrMoreCardsCondition.instance, "{this} deals 3 damage to target creature. " +
                "If you've drawn two or more cards this turn, {this} deals 5 damage to that creature instead"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Galvanize(final Galvanize card) {
        super(card);
    }

    @Override
    public Galvanize copy() {
        return new Galvanize(this);
    }
}
