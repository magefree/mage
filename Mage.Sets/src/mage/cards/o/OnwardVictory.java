
package mage.cards.o;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.target.common.TargetCreaturePermanent;

public final class OnwardVictory extends SplitCard {

    public OnwardVictory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{2}{R}", "{2}{W}", SpellAbilityType.SPLIT_AFTERMATH);

        // Onward
        // Target creature gets +X/+0 until end of turn where X is its power.
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        getLeftHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(TargetPermanentPowerCount.instance, StaticValue.get(0), Duration.EndOfTurn));

        // to
        // Victory
        // Target creature gains double strike until end of turn.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        Effect effect = new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn);
        getRightHalfCard().getSpellAbility().addEffect(effect);
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private OnwardVictory(final OnwardVictory card) {
        super(card);
    }

    @Override
    public OnwardVictory copy() {
        return new OnwardVictory(this);
    }
}
