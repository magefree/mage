package mage.cards.p;

import java.util.UUID;

import mage.abilities.condition.common.ForetoldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class PoisonTheCup extends CardImpl {

    public PoisonTheCup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{B}");

        // Destroy target creature. If this spell was foretold, scry 2.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new ScryEffect(2), ForetoldCondition.instance));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Foretell {1}{B}
        this.addAbility(new ForetellAbility(this, "{1}{B}"));
    }

    private PoisonTheCup(final PoisonTheCup card) {
        super(card);
    }

    @Override
    public PoisonTheCup copy() {
        return new PoisonTheCup(this);
    }
}