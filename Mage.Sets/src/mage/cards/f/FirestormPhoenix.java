package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author RobertFrosty, xenohedron
 */

public final class FirestormPhoenix extends CardImpl {

    public FirestormPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If Firestorm Phoenix would die, return Firestorm Phoenix to its owner's hand instead.
        // Until that player's next turn, that player plays with that card revealed in their hand and can't play it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new FirestormPhoenixEffect()));
    }

    private FirestormPhoenix(final FirestormPhoenix card) {
        super(card);
    }

    @Override
    public FirestormPhoenix copy() {
        return new FirestormPhoenix(this);
    }
}

class FirestormPhoenixEffect extends ReplacementEffectImpl {


    FirestormPhoenixEffect() {
        super(Duration.Custom, Outcome.ReturnToHand);
        staticText = "If {this} would die, return it to its owner's hand instead. " +
                "Until that player's next turn, that player plays with that card revealed in their hand and can't play it";
    }

    private FirestormPhoenixEffect(final FirestormPhoenixEffect effect) {
        super(effect);
    }

    @Override
    public FirestormPhoenixEffect copy() {
        return new FirestormPhoenixEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        if (controller == null || permanent == null) {
            return false;
        }
        controller.moveCards(permanent, Zone.HAND, source, game);
        Card card = game.getCard(permanent.getId());
        if (card == null) {
            return true;
        }
        game.addEffect(new FirestormPhoenixRevealEffect().setTargetPointer(new FixedTarget(card, game)), source);
        game.addEffect(new FirestormPhoenixRestrictEffect().setTargetPointer(new FixedTarget(card, game)), source);
        return true;

    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getTargetId())
                && ((ZoneChangeEvent) event).isDiesEvent();
    }
}

class FirestormPhoenixRevealEffect extends ContinuousEffectImpl {

    private int startingTurnNum;

    FirestormPhoenixRevealEffect() {
        super(Duration.Custom, Layer.PlayerEffects, SubLayer.NA, Outcome.Detriment);
    }

    private FirestormPhoenixRevealEffect(final FirestormPhoenixRevealEffect effect) {
        super(effect);
        this.startingTurnNum = effect.startingTurnNum;
    }

    @Override
    public FirestormPhoenixRevealEffect copy() {
        return new FirestormPhoenixRevealEffect(this);
    }


    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        startingTurnNum = game.getTurnNum();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getCard(getTargetPointer().getFirst(game, source)) == null) {
            return true;
        }
        return game.isActivePlayer(game.getOwnerId(getTargetPointer().getFirst(game, source)))
                && game.getTurnNum() > startingTurnNum;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            discard();
            return false;
        }
        Player player = game.getPlayer(card.getOwnerId());
        if (player != null) {
            player.revealCards(card.getIdName(), new CardsImpl(card), game, false);
        }
        return true;
    }
}

class FirestormPhoenixRestrictEffect extends ContinuousRuleModifyingEffectImpl {

    private int startingTurnNum;

    FirestormPhoenixRestrictEffect() {
        super(Duration.Custom, Outcome.Detriment);
    }

    private FirestormPhoenixRestrictEffect(final FirestormPhoenixRestrictEffect effect) {
        super(effect);
        this.startingTurnNum = effect.startingTurnNum;
    }

    @Override
    public FirestormPhoenixRestrictEffect copy() {
        return new FirestormPhoenixRestrictEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        startingTurnNum = game.getTurnNum();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getCard(getTargetPointer().getFirst(game, source)) == null) {
            return true;
        }
        return game.isActivePlayer(game.getOwnerId(getTargetPointer().getFirst(game, source)))
                && game.getTurnNum() > startingTurnNum;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card cardInHand = game.getCard(getTargetPointer().getFirst(game, source));
        SpellAbility spellAbility = SpellAbility.getSpellAbilityFromEvent(event, game);
        if (cardInHand == null || spellAbility == null) {
            return false;
        }
        Card cardToCast = spellAbility.getCharacteristics(game);
        return cardToCast != null && cardToCast.getId().equals(cardInHand.getId());
    }
}
