package mage.cards.c;

import mage.abilities.abilityword.StriveAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ColossalHeroics extends CardImpl {

    public ColossalHeroics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Strive - Colossal Heroics costs {1}{G} more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{1}{G}"));

        // Any number of target creatures each get +2/+2 until end of turn. Untap those creatures.
        Effect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        effect.setText("Any number of target creatures each get +2/+2 until end of turn");
        this.getSpellAbility().addEffect(effect);
        effect = new UntapTargetEffect();
        effect.setText("Untap those creatures");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
    }

    private ColossalHeroics(final ColossalHeroics card) {
        super(card);
    }

    @Override
    public ColossalHeroics copy() {
        return new ColossalHeroics(this);
    }
}
