
package mage.cards.e;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.permanent.token.SpiritEvilBorosCharmToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EvilBorosCharm extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("unblocked attacking creatures");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(UnblockedPredicate.instance);
    }

    public EvilBorosCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B/R}{W/B}");

        // Choose one —
        // • Evil Boros Charm deals 2 damage to any target and you gain 2 life.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("and"));

        // • Unblocked attacking creatures get +1/+0 until end of turn.
        Mode mode = new Mode(new BoostAllEffect(1, 0, Duration.EndOfTurn, filter, false));
        this.getSpellAbility().addMode(mode);

        // • Create a 1/1 colorless Spirit creature token with lifelink and haste.
        mode = new Mode(new CreateTokenEffect(new SpiritEvilBorosCharmToken()));
        this.getSpellAbility().addMode(mode);
    }

    private EvilBorosCharm(final EvilBorosCharm card) {
        super(card);
    }

    @Override
    public EvilBorosCharm copy() {
        return new EvilBorosCharm(this);
    }
}
