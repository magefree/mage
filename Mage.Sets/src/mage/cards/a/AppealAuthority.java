package mage.cards.a;

import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.AftermathAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AppealAuthority extends SplitCard {

    public AppealAuthority(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{G}", "{1}{W}", SpellAbilityType.SPLIT_AFTERMATH);

        // Appeal
        // Until end of turn, target creature gains trample and gets +X/+X, where X is the number of creatures you control.
        getLeftHalfCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setText("Until end of turn, target creature gains trample"));
        getLeftHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(CreaturesYouControlCount.instance, CreaturesYouControlCount.instance, Duration.EndOfTurn, true)
                .setText("and gets +X/+X, where X is the number of creatures you control"));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        getLeftHalfCard().getSpellAbility().addHint(CreaturesYouControlHint.instance);

        // Authority
        // Aftermath
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Tap up to two target creatures your opponents control. Creatures you control gain vigilance until end of turn.
        getRightHalfCard().getSpellAbility().addEffect(new TapTargetEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent(0, 2));
        getRightHalfCard().getSpellAbility().addEffect(new GainAbilityControlledEffect(VigilanceAbility.getInstance(),
                Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES));

    }

    private AppealAuthority(final AppealAuthority card) {
        super(card);
    }

    @Override
    public AppealAuthority copy() {
        return new AppealAuthority(this);
    }
}
