package mage.cards.b;

import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloomingBlast extends CardImpl {

    public BloomingBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Gift a Treasure
        this.addAbility(new GiftAbility(this, GiftType.TREASURE));

        // Blooming Blast deals 2 damage to target creature. If the gift was promised, Blooming Blast also deals 3 damage to that creature's controller.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetControllerEffect(3), GiftWasPromisedCondition.TRUE,
                "if the gift was promised, {this} also deals 3 damage to that creature's controller"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BloomingBlast(final BloomingBlast card) {
        super(card);
    }

    @Override
    public BloomingBlast copy() {
        return new BloomingBlast(this);
    }
}
