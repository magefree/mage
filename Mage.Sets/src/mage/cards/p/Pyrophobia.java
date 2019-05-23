package mage.cards.p;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Pyrophobia extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.COWARD, "Cowards");

    public Pyrophobia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Pyrophobia deals 3 damage to target creature. Cowards can't block this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CantBlockAllEffect(filter, Duration.EndOfTurn));
    }

    private Pyrophobia(final Pyrophobia card) {
        super(card);
    }

    @Override
    public Pyrophobia copy() {
        return new Pyrophobia(this);
    }
}
