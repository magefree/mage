package mage.cards.o;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OjerAxonilDeepestMight extends CardImpl {

    public OjerAxonilDeepestMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.secondSideCardClazz = mage.cards.t.TempleOfPower.class;

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // If a red source you control would deal an amount of noncombat damage less than Ojer Axonil's power to an opponent, that source deals damage equal to Ojer Axonil's power instead.
        this.addAbility(new SimpleStaticAbility(new OjerAxonilDeepestMightReplacementEffect()));

        // When Ojer Axonil dies, return it to the battlefield tapped and transformed under its owner's control.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesSourceTriggeredAbility(new OjerAxonilDeepestMightTransformEffect()));
    }

    private OjerAxonilDeepestMight(final OjerAxonilDeepestMight card) {
        super(card);
    }

    @Override
    public OjerAxonilDeepestMight copy() {
        return new OjerAxonilDeepestMight(this);
    }
}

// Inspired by Edgar, Charmed Groom
class OjerAxonilDeepestMightTransformEffect extends OneShotEffect {

    OjerAxonilDeepestMightTransformEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield tapped and transformed under its owner's control";
    }

    private OjerAxonilDeepestMightTransformEffect(final OjerAxonilDeepestMightTransformEffect effect) {
        super(effect);
    }

    @Override
    public OjerAxonilDeepestMightTransformEffect copy() {
        return new OjerAxonilDeepestMightTransformEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (!(sourceObject instanceof Card)) {
            return false;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        controller.moveCards((Card) sourceObject, Zone.BATTLEFIELD, source, game, true, false, true, null);
        return true;
    }
}

// Inspired by Torbran, Thane of Red Fell
class OjerAxonilDeepestMightReplacementEffect extends ReplacementEffectImpl {

    OjerAxonilDeepestMightReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        this.staticText = "If a red source you control would deal an amount of noncombat damage less "
                + "than {this}'s power to an opponent, that source deals damage equal to {this}'s power instead.";
    }

    private OjerAxonilDeepestMightReplacementEffect(final OjerAxonilDeepestMightReplacementEffect effect) {
        super(effect);
    }

    @Override
    public OjerAxonilDeepestMightReplacementEffect copy() {
        return new OjerAxonilDeepestMightReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent ojer = source.getSourcePermanentIfItStillExists(game);
        if (ojer != null && ojer.getPower().getValue() > 0) {
            event.setAmount(ojer.getPower().getValue());
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        // Is damage to an opponent?
        if (controller == null || !controller.hasOpponent(event.getTargetId(), game)) {
            return false;
        }
        MageObject sourceObject;
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (sourcePermanent == null) {
            sourceObject = game.getObject(event.getSourceId());
        } else {
            sourceObject = sourcePermanent;
        }
        Permanent ojer = source.getSourcePermanentIfItStillExists(game);
        DamageEvent dmgEvent = (DamageEvent) event;

        return sourceObject != null
                && ojer != null
                && dmgEvent != null
                && sourceObject.getColor(game).isRed()
                && !dmgEvent.isCombatDamage()
                && event.getAmount() > 0
                && event.getAmount() < ojer.getPower().getValue();
    }
}
