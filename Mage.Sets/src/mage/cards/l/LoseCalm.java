
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class LoseCalm extends CardImpl {

    public LoseCalm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Gain control of target creature until end of turn. Untap that creature. It gains haste and menace until end of turn. (A creature with menace can't be blocked except by two or more creatures.)
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap that creature");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn, "It gains haste"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        effect = new GainAbilityTargetEffect(new MenaceAbility(), Duration.EndOfTurn);
        effect.setText("and menace until end of turn." +
                "<i>(A creature with menace can't be blocked except by two or more creatures.)</i>");
        this.getSpellAbility().addEffect(effect);
    }

    private LoseCalm(final LoseCalm card) {
        super(card);
    }

    @Override
    public LoseCalm copy() {
        return new LoseCalm(this);
    }
}
