
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public final class BaronVonCount extends CardImpl {

    public BaronVonCount(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Baron Von Count enters the battlefield with a doom counter on "5."
        this.addAbility(new EntersBattlefieldAbility(new BaronVonCountPutCounterEffect()));

        // Whenever you cast a spell with the indicated numeral in its mana cost, text box, power, or toughness, move the doom counter one numeral to the left.
        this.addAbility(new BaronVonCountTriggeredAbility());

        // When the doom counter moves from "1," destroy target player and put that doom counter on "5."
        this.addAbility(new BaronVonCountSecondTriggeredAbility());
    }

    private BaronVonCount(final BaronVonCount card) {
        super(card);
    }

    @Override
    public BaronVonCount copy() {
        return new BaronVonCount(this);
    }
}

class BaronVonCountPutCounterEffect extends OneShotEffect {

    public BaronVonCountPutCounterEffect() {
        super(Outcome.Benefit);
        staticText = "with a doom counter on \"5.\"";
    }

    public BaronVonCountPutCounterEffect(final BaronVonCountPutCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source);
        }
        if (controller != null && mageObject != null) {
            Integer doomNumber = 5;
            game.getState().setValue(mageObject.getId() + "_doom", doomNumber);
            if (mageObject instanceof Permanent) {
                ((Permanent) mageObject).addInfo("doom counter", CardUtil.addToolTipMarkTags("Doom counter at: " + doomNumber), game);
                // This isn't exactly correct - from what I understand from Maro's rulings, the game "can't see" the counter on Baron (i.e. the original counter can't be removed by Vampire Hexmage etc.), 
                // but it can still be proliferated to put an additional doom counter on itself (the new counters can be removed and aren't placed on the "numbers" - i.e. they don't influence the card's 
                // functionality in any direct way). To simplify things, I merely put a do-nothing Doom counter on Baron that can be proliferated, etc., in addition to the value that tracks the 
                // the placement of the functional "counter". This only has fringe incorrect interactions with a few cards like Thief of Blood which now gets an extra counter from Baron.
                new AddCountersSourceEffect(CounterType.DOOM.createInstance()).apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public BaronVonCountPutCounterEffect copy() {
        return new BaronVonCountPutCounterEffect(this);
    }
}

class BaronVonCountTriggeredAbility extends TriggeredAbilityImpl {

    public BaronVonCountTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BaronVonCountMoveDoomCounterEffect());
        setTriggerPhrase("Whenever you cast a spell with the indicated numeral in its mana cost, text box, power, or toughness, ");
    }

    public BaronVonCountTriggeredAbility(final BaronVonCountTriggeredAbility abiltity) {
        super(abiltity);
    }

    @Override
    public BaronVonCountTriggeredAbility copy() {
        return new BaronVonCountTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Permanent sourcePermanent = game.getPermanent(getSourceId());
            MageObject mageObject = game.getObject(getSourceId());
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell == null || sourcePermanent == null || mageObject == null) {
                return false;
            }
            Integer doomNumber = (Integer) game.getState().getValue(mageObject.getId() + "_doom");
            if (doomNumber == null || doomNumber == 0) {
                return false;
            }
            if (!spell.isFaceDown(game)) {
                String doomString = doomNumber.toString();
                if (spell.getCard().getManaCost().getText().contains(doomString)
                        || String.valueOf(spell.getPower().getBaseValue()).contains(doomString)
                        || String.valueOf(spell.getToughness().getBaseValue()).contains(doomString)) {
                    return true;
                } else {
                    for (String string : spell.getCard().getRules(game)) {
                        if (string.contains(doomString)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

class BaronVonCountMoveDoomCounterEffect extends OneShotEffect {

    public BaronVonCountMoveDoomCounterEffect() {
        super(Outcome.Neutral);
        staticText = "move the doom counter one numeral to the left";
    }

    public BaronVonCountMoveDoomCounterEffect(final BaronVonCountMoveDoomCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        MageObject mageObject = game.getObject(source);
        if (controller != null && sourcePermanent != null && mageObject != null) {
            if (game.getState().getValue(mageObject.getId() + "_doom") == null) {
                return false;
            }
            Integer doomNumber = (Integer) game.getState().getValue(mageObject.getId() + "_doom");
            if (doomNumber == 1) {
                // not completely sure if counter should be moving here or not (relevant in case the second trigger gets countered)
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CUSTOM_EVENT, source.getSourceId(), source, controller.getId(), "DoomCounterReset", 1));
            }
            if (doomNumber > 0) {
                doomNumber--;
                game.getState().setValue(mageObject.getId() + "_doom", doomNumber);
                ((Permanent) mageObject).addInfo("doom counter", CardUtil.addToolTipMarkTags("Doom counter at: " + doomNumber), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public BaronVonCountMoveDoomCounterEffect copy() {
        return new BaronVonCountMoveDoomCounterEffect(this);
    }
}

class BaronVonCountSecondTriggeredAbility extends TriggeredAbilityImpl {

    public BaronVonCountSecondTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BaronVonCountDestroyPlayerEffect());
        this.addTarget(new TargetPlayer());
        setTriggerPhrase("When the doom counter moves from \"1,\" ");
    }

    public BaronVonCountSecondTriggeredAbility(BaronVonCountSecondTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CUSTOM_EVENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getData().equals("DoomCounterReset") && event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public BaronVonCountSecondTriggeredAbility copy() {
        return new BaronVonCountSecondTriggeredAbility(this);
    }
}

class BaronVonCountDestroyPlayerEffect extends OneShotEffect {

    public BaronVonCountDestroyPlayerEffect() {
        super(Outcome.Neutral);
        staticText = "destroy target player and put that doom counter on \"5.\"";
    }

    public BaronVonCountDestroyPlayerEffect(final BaronVonCountDestroyPlayerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null && targetPlayer.canLose(game)) {
            game.informPlayers(targetPlayer.getLogName() + " was destroyed");
            targetPlayer.lost(game); // double checks canLose, but seems more future-proof than lostForced
        }
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        MageObject mageObject = game.getObject(source);
        if (sourcePermanent != null && mageObject != null) {
            if (game.getState().getValue(mageObject.getId() + "_doom") == null) {
                return false;
            }
            Integer doomNumber = 5;
            game.getState().setValue(mageObject.getId() + "_doom", doomNumber);
            ((Permanent) mageObject).addInfo("doom counter", CardUtil.addToolTipMarkTags("Doom counter at: " + doomNumber), game);
            return true;
        }
        return false;
    }

    @Override
    public BaronVonCountDestroyPlayerEffect copy() {
        return new BaronVonCountDestroyPlayerEffect(this);
    }
}
