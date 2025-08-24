package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAnchorWordAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ModeChoice;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CitadelSiege extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature controlled by the active player");

    static {
        filter.add(TargetController.ACTIVE.getControllerPredicate());
    }

    public CitadelSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // As Citadel Siege enters the battlefield, choose Khans or Dragons.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseModeEffect(ModeChoice.KHANS, ModeChoice.DRAGONS)));

        // * Khans - At the beginning of combat on your turn, put two +1/+1 counters on target creature you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(ability, ModeChoice.KHANS)));

        // * Dragons - At the beginning of combat on each opponent's turn, tap target creature that player controls.
        ability = new BeginningOfCombatTriggeredAbility(
                TargetController.OPPONENT, new TapTargetEffect("tap target creature that player controls"), false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(ability, ModeChoice.DRAGONS)));
    }

    private CitadelSiege(final CitadelSiege card) {
        super(card);
    }

    @Override
    public CitadelSiege copy() {
        return new CitadelSiege(this);
    }
}
