package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TeferiMageOfZhalfir extends CardImpl {

    public TeferiMageOfZhalfir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Creature cards you own that aren't on the battlefield have flash.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TeferiMageOfZhalfirAddFlashEffect()));

        // Each opponent can cast spells only any time they could cast a sorcery.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TeferiMageOfZhalfirReplacementEffect()));

    }

    private TeferiMageOfZhalfir(final TeferiMageOfZhalfir card) {
        super(card);
    }

    @Override
    public TeferiMageOfZhalfir copy() {
        return new TeferiMageOfZhalfir(this);
    }
}

class TeferiMageOfZhalfirAddFlashEffect extends ContinuousEffectImpl {

    public TeferiMageOfZhalfirAddFlashEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Creature cards you own that aren't on the battlefield have flash";
    }

    public TeferiMageOfZhalfirAddFlashEffect(final TeferiMageOfZhalfirAddFlashEffect effect) {
        super(effect);
    }

    @Override
    public TeferiMageOfZhalfirAddFlashEffect copy() {
        return new TeferiMageOfZhalfirAddFlashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // in graveyard
            for (UUID cardId : controller.getGraveyard()) {
                Card card = game.getCard(cardId);
                if (card != null && card.isCreature(game)) {
                    game.getState().addOtherAbility(card, FlashAbility.getInstance());
                }
            }
            // on Hand
            for (UUID cardId : controller.getHand()) {
                Card card = game.getCard(cardId);
                if (card != null && card.isCreature(game)) {
                    game.getState().addOtherAbility(card, FlashAbility.getInstance());
                }
            }
            // in Exile
            for (Card card : game.getState().getExile().getAllCards(game)) {
                if (card.isOwnedBy(controller.getId()) && card.isCreature(game)) {
                    game.getState().addOtherAbility(card, FlashAbility.getInstance());
                }
            }
            // in Library (e.g. for Mystical Teachings)
            for (Card card : controller.getLibrary().getCards(game)) {
                if (card.isOwnedBy(controller.getId()) && card.isCreature(game)) {
                    game.getState().addOtherAbility(card, FlashAbility.getInstance());
                }
            }
            // cards in command zone
            game.getCommanderCardsFromCommandZone(controller, CommanderCardType.ANY).stream()
                    .filter(card1 -> card1.isCreature(game))
                    .forEach(card -> {
                        game.getState().addOtherAbility(card, FlashAbility.getInstance());
                    });
            return true;
        }
        return false;
    }
}

class TeferiMageOfZhalfirReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    TeferiMageOfZhalfirReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Each opponent can cast spells only any time they could cast a sorcery";
    }

    TeferiMageOfZhalfirReplacementEffect(final TeferiMageOfZhalfirReplacementEffect effect) {
        super(effect);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can cast spells only any time you could cast a sorcery  (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.hasOpponent(event.getPlayerId(), game)) {
            return !game.canPlaySorcery(event.getPlayerId());
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TeferiMageOfZhalfirReplacementEffect copy() {
        return new TeferiMageOfZhalfirReplacementEffect(this);
    }
}
