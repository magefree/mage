package mage.cards.b;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class BorosCharm extends CardImpl {

    public BorosCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}");

        //Choose one - Boros Charm deals 4 damage to target player
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        //or permanents you control are indestructible this turn
        this.getSpellAbility().addMode(new Mode(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        )));
        //or target creature gains double strike until end of turn.
        Mode mode2 = new Mode(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn));
        mode2.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode2);
    }

    private BorosCharm(final BorosCharm card) {
        super(card);
    }

    @Override
    public BorosCharm copy() {
        return new BorosCharm(this);
    }
}
