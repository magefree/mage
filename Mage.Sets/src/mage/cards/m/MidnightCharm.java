
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class MidnightCharm extends CardImpl {

    public MidnightCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Choose one - Midnight Charm deals 1 damage to target creature and you gain 1 life; or target creature gains first strike until end of turn; or tap target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addEffect(new GainLifeEffect(1).setText("and you gain 1 life"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Mode mode = new Mode(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        mode = new Mode(new TapTargetEffect());
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private MidnightCharm(final MidnightCharm card) {
        super(card);
    }

    @Override
    public MidnightCharm copy() {
        return new MidnightCharm(this);
    }
}
