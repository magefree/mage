package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 * @author spjspj
 */
public final class CurseOfVengeance extends CardImpl {

    public CurseOfVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever enchanted player casts a spell, put a spite counter on Curse of Vengeance.
        this.addAbility(new CurseOfVengeanceTriggeredAbility());

        // When enchanted player loses the game, you gain X life and draw X cards, where X is the number of spite counters on Curse of Vengeance.
        this.addAbility(new CurseOfVengeancePlayerLosesTriggeredAbility());
    }

    private CurseOfVengeance(final CurseOfVengeance card) {
        super(card);
    }

    @Override
    public CurseOfVengeance copy() {
        return new CurseOfVengeance(this);
    }
}

class CurseOfVengeanceTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfVengeanceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.SPITE.createInstance(), Outcome.Detriment), false);
    }

    public CurseOfVengeanceTriggeredAbility(Effect effect, boolean optional, String text) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public CurseOfVengeanceTriggeredAbility(final CurseOfVengeanceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;

    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(getSourceId());
        Spell spell = game.getStack().getSpell(event.getSourceId());

        if (enchantment != null && spell != null
                && enchantment.isAttachedTo(spell.getControllerId())) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(getSourceId(), game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted player casts a spell, put a spite counter on {this}";
    }

    @Override
    public CurseOfVengeanceTriggeredAbility copy() {
        return new CurseOfVengeanceTriggeredAbility(this);
    }
}

class CurseOfVengeancePlayerLosesTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfVengeancePlayerLosesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CurseOfVengeanceDrawLifeEffect(), false);
    }

    public CurseOfVengeancePlayerLosesTriggeredAbility(final CurseOfVengeancePlayerLosesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfVengeancePlayerLosesTriggeredAbility copy() {
        return new CurseOfVengeancePlayerLosesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(this.getSourceId());
        return sourceObject != null && sourceObject.isAttachedTo(event.getPlayerId());
    }

    @Override
    public String getRule() {
        return "When enchanted player loses the game, you gain X life and "
                + "draw X cards, where X is the number of spite counters on {this}";
    }
}

class CurseOfVengeanceDrawLifeEffect extends OneShotEffect {

    public CurseOfVengeanceDrawLifeEffect() {
        super(Outcome.Benefit);
        staticText = "you gain X life and draw X cards, where X is the "
                + "number of spite counters on {this}";
    }

    public CurseOfVengeanceDrawLifeEffect(final CurseOfVengeanceDrawLifeEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfVengeanceDrawLifeEffect copy() {
        return new CurseOfVengeanceDrawLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject != null && controller != null) {
            if (sourceObject.getCounters(game).containsKey(CounterType.SPITE)) {
                controller.drawCards(sourceObject.getCounters(game).getCount(CounterType.SPITE), source, game);
                controller.gainLife(sourceObject.getCounters(game).getCount(CounterType.SPITE), game, source);
            }
            return true;
        }
        return false;
    }
}
