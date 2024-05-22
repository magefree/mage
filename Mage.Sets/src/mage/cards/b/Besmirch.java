package mage.cards.b;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Besmirch extends CardImpl {

    public Besmirch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Until end of turn, gain control of target creature and it gains haste. Untap and goad that creature.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn)
                .setText("Until end of turn, gain control of target creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                .setText("and it gains haste"));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap"));
        this.getSpellAbility().addEffect(new GoadTargetEffect()
                .setText("and goad that creature. <i>(Until your next turn, that creature " +
                        "attacks each combat if able and attacks a player other than you if able.)</i>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Besmirch(final Besmirch card) {
        super(card);
    }

    @Override
    public Besmirch copy() {
        return new Besmirch(this);
    }
}
