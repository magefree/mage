package mage.cards.n;

import mage.Mana;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NarsetsRebuke extends CardImpl {

    public NarsetsRebuke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Narset's Rebuke deals 5 damage to target creature. Add {U}{R}{W}. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BasicManaEffect(new Mana(
                1, 1, 0, 1, 0, 0, 0, 0
        )));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
    }

    private NarsetsRebuke(final NarsetsRebuke card) {
        super(card);
    }

    @Override
    public NarsetsRebuke copy() {
        return new NarsetsRebuke(this);
    }
}
