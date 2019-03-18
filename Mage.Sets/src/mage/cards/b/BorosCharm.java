
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author Plopman
 */
public final class BorosCharm extends CardImpl {

    public BorosCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}");

        //Choose one - Boros Charm deals 4 damage to target player
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        //or permanents you control are indestructible this turn
        Mode mode = new Mode();
        Effect effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledPermanent(), false);
        effect.setText("permanents you control are indestructible this turn");
        mode.addEffect(effect);
        this.getSpellAbility().addMode(mode);
        //or target creature gains double strike until end of turn.
        Mode mode2 = new Mode();
        mode2.addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn));
        mode2.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode2);
    }

    public BorosCharm(final BorosCharm card) {
        super(card);
    }

    @Override
    public BorosCharm copy() {
        return new BorosCharm(this);
    }
}
