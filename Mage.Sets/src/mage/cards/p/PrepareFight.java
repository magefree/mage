package mage.cards.p;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author stravant
 */
public final class PrepareFight extends SplitCard {

    public PrepareFight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{1}{W}", "{3}{G}", SpellAbilityType.SPLIT_AFTERMATH);

        // Prepare
        // Untap target creature. It gets +2/+2 and gains lifelink until end of turn.
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap target creature");
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        effect.setText("It gets +2/+2");
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains lifelink until end of turn");
        getLeftHalfCard().getSpellAbility().addEffect(effect);

        // to
        // Fight
        // Target creature you control fights target creature you don't control.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addEffect(new FightTargetsEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        getRightHalfCard().getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private PrepareFight(final PrepareFight card) {
        super(card);
    }

    @Override
    public PrepareFight copy() {
        return new PrepareFight(this);
    }
}
