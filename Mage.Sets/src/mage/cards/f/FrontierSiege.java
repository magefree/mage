
package mage.cards.f;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class FrontierSiege extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with flying");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("a creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(new ControllerPredicate(TargetController.NOT_YOU));
    }
    private final static String ruleTrigger1 = "&bull Khans &mdash; At the beginning of each of your main phases, add {G}{G}.";
    private final static String ruleTrigger2 = "&bull Dragons &mdash; Whenever a creature with flying enters the battlefield under your control, you may have it fight target creature you don't control.";

    public FrontierSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}");

        // As Frontier Siege enters the battlefield, choose Khans or Dragons.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Khans or Dragons?", "Khans", "Dragons"), null,
                "As {this} enters the battlefield, choose Khans or Dragons.", ""));

        // * Khans - At the beginning of each of your main phases, add {G}{G}.
        this.addAbility(new ConditionalTriggeredAbility(
                new FrontierSiegeKhansTriggeredAbility(),
                new ModeChoiceSourceCondition("Khans"),
                ruleTrigger1));

        // * Dragons - Whenever a creature with flying enters the battlefield under your control, you may have it fight target creature you don't control.
        Ability ability2 = new ConditionalTriggeredAbility(
                new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new FrontierSiegeFightEffect(), filter, true, SetTargetPointer.PERMANENT, ""),
                new ModeChoiceSourceCondition("Dragons"),
                ruleTrigger2);
        ability2.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability2);

    }

    public FrontierSiege(final FrontierSiege card) {
        super(card);
    }

    @Override
    public FrontierSiege copy() {
        return new FrontierSiege(this);
    }
}

class FrontierSiegeKhansTriggeredAbility extends TriggeredAbilityImpl {

    public FrontierSiegeKhansTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaToManaPoolSourceControllerEffect(Mana.GreenMana(2)), false);

    }

    public FrontierSiegeKhansTriggeredAbility(final FrontierSiegeKhansTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FrontierSiegeKhansTriggeredAbility copy() {
        return new FrontierSiegeKhansTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PRECOMBAT_MAIN_PHASE_PRE
                || event.getType() == GameEvent.EventType.POSTCOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public String getRule() {
        return "At the beginning of each of your main phases, " + super.getRule();
    }

}

class FrontierSiegeFightEffect extends OneShotEffect {

    FrontierSiegeFightEffect() {
        super(Outcome.Damage);
    }

    FrontierSiegeFightEffect(final FrontierSiegeFightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeredCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (triggeredCreature != null
                && target != null
                && triggeredCreature.isCreature()
                && target.isCreature()) {
            triggeredCreature.fight(target, source, game);
            return true;
        }
        return false;
    }

    @Override
    public FrontierSiegeFightEffect copy() {
        return new FrontierSiegeFightEffect(this);
    }
}
