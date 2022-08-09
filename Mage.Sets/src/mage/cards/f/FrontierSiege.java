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
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FrontierSiege extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with flying");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    private static final String ruleTrigger1 = "&bull  Khans &mdash; At the beginning of each of your main phases, add {G}{G}.";
    private static final String ruleTrigger2 = "&bull  Dragons &mdash; Whenever a creature with flying enters the battlefield under your control, you may have it fight target creature you don't control.";

    public FrontierSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

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
        ability2.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability2);

    }

    private FrontierSiege(final FrontierSiege card) {
        super(card);
    }

    @Override
    public FrontierSiege copy() {
        return new FrontierSiege(this);
    }
}

class FrontierSiegeKhansTriggeredAbility extends TriggeredAbilityImpl {

    FrontierSiegeKhansTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaToManaPoolSourceControllerEffect(Mana.GreenMana(2)), false);
        setTriggerPhrase("At the beginning of each of your main phases, ");
    }

    private FrontierSiegeKhansTriggeredAbility(final FrontierSiegeKhansTriggeredAbility ability) {
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
}

class FrontierSiegeFightEffect extends OneShotEffect {

    FrontierSiegeFightEffect() {
        super(Outcome.Damage);
    }

    private FrontierSiegeFightEffect(final FrontierSiegeFightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeredCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (triggeredCreature != null
                && target != null
                && triggeredCreature.isCreature(game)
                && target.isCreature(game)) {
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
