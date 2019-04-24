
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class CitadelSiege extends CardImpl {

    private final static String ruleTrigger1 = "&bull Khans &mdash; At the beginning of combat on your turn, put two +1/+1 counters on target creature you control.";
    private final static String ruleTrigger2 = "&bull Dragons &mdash; At the beginning of combat on each opponent's turn, tap target creature that player controls.";

    public CitadelSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // As Citadel Siege enters the battlefield, choose Khans or Dragons.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Khans or Dragons?","Khans", "Dragons"),null,
                "As {this} enters the battlefield, choose Khans or Dragons.",""));

        // * Khans - At the beginning of combat on your turn, put two +1/+1 counters on target creature you control.
        Ability ability = new ConditionalTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)), TargetController.YOU, false),
                new ModeChoiceSourceCondition("Khans"),
                ruleTrigger1);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // * Dragons - At the beginning of combat on each opponent's turn, tap target creature that player controls.
        ability = new ConditionalTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new TapTargetEffect(), TargetController.OPPONENT, false),
                new ModeChoiceSourceCondition("Dragons"),
                ruleTrigger2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (this.getAbilities().contains(ability) && ability.getRule().startsWith("&bull Dragons")) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that player controls");
            filter.add(new ControllerIdPredicate(game.getCombat().getAttackingPlayerId()));
            ability.getTargets().clear();
            ability.addTarget(new TargetCreaturePermanent(filter));
        }
    }


    public CitadelSiege(final CitadelSiege card) {
        super(card);
    }

    @Override
    public CitadelSiege copy() {
        return new CitadelSiege(this);
    }
}
