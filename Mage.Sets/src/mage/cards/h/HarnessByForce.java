package mage.cards.h;

import mage.abilities.abilityword.StriveAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
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
 * @author LevelX2
 */
public final class HarnessByForce extends CardImpl {

    public HarnessByForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Strive - Harness by Force costs {2}{R} more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{2}{R}"));

        // Gain control of any number of target creatures until end of turn. Untap those creatures. They gain haste until end of turn.
        Effect effect = new GainControlTargetEffect(Duration.EndOfTurn);
        effect.setText("Gain control of any number of target creatures until end of turn");
        this.getSpellAbility().addEffect(effect);
        effect = new UntapTargetEffect();
        effect.setText("Untap those creatures");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn, "They gain haste until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
    }

    private HarnessByForce(final HarnessByForce card) {
        super(card);
    }

    @Override
    public HarnessByForce copy() {
        return new HarnessByForce(this);
    }
}
