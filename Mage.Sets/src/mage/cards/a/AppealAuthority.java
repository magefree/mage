
package mage.cards.a;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AppealAuthority extends SplitCard {

    public AppealAuthority(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{G}", "{1}{W}", SpellAbilityType.SPLIT_AFTERMATH);

        // Appeal
        // Until end of turn, target creature gains trample and gets +X/+X, where X is the number of creatures you control.
        getLeftHalfCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setText("Until end of turn, target creature gains trample"));
        DynamicValue controlledCreatures = new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent("the number of creatures you control"));
        getLeftHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(controlledCreatures, controlledCreatures, Duration.EndOfTurn, true)
                .setText("and gets +X/+X, where X is the number of creatures you control"));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Authority
        // Aftermath
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Tap up to two target creatures your opponents control. Creatures you control gain vigilance until end of turn.
        getRightHalfCard().getSpellAbility().addEffect(new TapTargetEffect());
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures your opponents control");
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2, filter, false));
        getRightHalfCard().getSpellAbility().addEffect(new GainAbilityControlledEffect(VigilanceAbility.getInstance(),
                Duration.EndOfTurn, new FilterControlledCreaturePermanent("creatures")));

    }

    public AppealAuthority(final AppealAuthority card) {
        super(card);
    }

    @Override
    public AppealAuthority copy() {
        return new AppealAuthority(this);
    }
}
