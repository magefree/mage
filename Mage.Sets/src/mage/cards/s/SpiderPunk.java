package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class SpiderPunk extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SPIDER, "Spiders you control");

    public SpiderPunk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Riot
        this.addAbility(new RiotAbility());

        // Other Spiders you control have riot.
        Ability ability = new SimpleStaticAbility(new SpiderPunkRiotETBEffect());
        ability.addEffect(new GainAbilityControlledEffect(new RiotAbility(), Duration.WhileOnBattlefield, filter, true)
                .setText(""));
        this.addAbility(ability);

        // Spells and abilities can't be countered.
        this.addAbility(new SimpleStaticAbility(new SpiderPunkCantCounterEffect()));

        // Damage can't be prevented.
        this.addAbility(new SimpleStaticAbility(new DamageCantBePreventedEffect(Duration.WhileOnBattlefield)));
    }

    private SpiderPunk(final SpiderPunk card) {
        super(card);
    }

    @Override
    public SpiderPunk copy() {
        return new SpiderPunk(this);
    }
}

class SpiderPunkCantCounterEffect extends ContinuousRuleModifyingEffectImpl {

    SpiderPunkCantCounterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Spells and abilities can't be countered";
    }


    private SpiderPunkCantCounterEffect(final SpiderPunkCantCounterEffect effect) {
        super(effect);
    }

    @Override
    public SpiderPunkCantCounterEffect copy() {
        return new SpiderPunkCantCounterEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Object object = game.getObject(event.getTargetId());
        return object instanceof Spell || object instanceof StackAbility;
    }

}

//TODO: Remove after fixing continuous effects working on entering permanents
class SpiderPunkRiotETBEffect extends ReplacementEffectImpl {

    SpiderPunkRiotETBEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "Other Spiders you control have riot";
    }

    private SpiderPunkRiotETBEffect(SpiderPunkRiotETBEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null
                && creature.getId() != source.getSourceId()
                && creature.isControlledBy(source.getControllerId())
                && creature.isCreature(game)
                && !(creature instanceof PermanentToken);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        Player player = game.getPlayer(source.getControllerId());
        if (creature == null || player == null) {
            return false;
        }
        if (player.chooseUse(
                outcome, "Have " + creature.getLogName() + " enter the battlefield with a +1/+1 counter on it or with haste?",
                null, "+1/+1 counter", "Haste", source, game
        )) {
            game.informPlayers(player.getLogName() + " choose to put a +1/+1 counter on " + creature.getName());
            creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        } else {
            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
            effect.setTargetPointer(new FixedTarget(creature.getId(), creature.getZoneChangeCounter(game) + 1));
            game.addEffect(effect, source);
        }
        return false;
    }

    @Override
    public SpiderPunkRiotETBEffect copy() {
        return new SpiderPunkRiotETBEffect(this);
    }
}