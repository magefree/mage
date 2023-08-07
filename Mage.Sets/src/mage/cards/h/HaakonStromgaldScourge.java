

package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Mainiack11
 */
public final class HaakonStromgaldScourge extends CardImpl {

    public HaakonStromgaldScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // You may cast Haakon, Stromgald Scourge from your graveyard, but not from anywhere else.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new HaakonStromgaldScourgePlayEffect());
        ability.addEffect(new HaakonStromgaldScourgePlayEffect2());
        this.addAbility(ability);

        // As long as Haakon is on the battlefield, you may play Knight cards from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HaakonPlayKnightsFromGraveyardEffect()));

        // When Haakon dies, you lose 2 life.
        this.addAbility(new DiesSourceTriggeredAbility(new LoseLifeSourceControllerEffect(2)));

    }

    private HaakonStromgaldScourge(final HaakonStromgaldScourge card) {
        super(card);
    }

    @Override
    public HaakonStromgaldScourge copy() {
        return new HaakonStromgaldScourge(this);
    }

}

class HaakonStromgaldScourgePlayEffect extends AsThoughEffectImpl {

    public HaakonStromgaldScourgePlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from your graveyard";
    }

    public HaakonStromgaldScourgePlayEffect(final HaakonStromgaldScourgePlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HaakonStromgaldScourgePlayEffect copy() {
        return new HaakonStromgaldScourgePlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(source.getSourceId()) &&
                affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(source.getSourceId());
            return card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD;
        }
        return false;
    }
}

class HaakonStromgaldScourgePlayEffect2 extends ContinuousRuleModifyingEffectImpl {

    public HaakonStromgaldScourgePlayEffect2() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = ", but not from anywhere else";
    }

    public HaakonStromgaldScourgePlayEffect2 (final HaakonStromgaldScourgePlayEffect2 effect) {
        super(effect);
    }

    @Override
    public HaakonStromgaldScourgePlayEffect2 copy() {
        return new HaakonStromgaldScourgePlayEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        if (card != null && card.getId().equals(source.getSourceId())) {
            Zone zone = game.getState().getZone(card.getId());
            return zone != null && (zone != Zone.GRAVEYARD);
        }
        return false;
    }
}

class HaakonPlayKnightsFromGraveyardEffect extends AsThoughEffectImpl {

    public HaakonPlayKnightsFromGraveyardEffect () {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as {this} is on the battlefield, you may cast Knight spells from your graveyard";
    }

    public HaakonPlayKnightsFromGraveyardEffect(final HaakonPlayKnightsFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HaakonPlayKnightsFromGraveyardEffect copy() {
        return new HaakonPlayKnightsFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {       
        if (affectedControllerId.equals(source.getControllerId())) {
            Card knightToCast = game.getCard(objectId);
            return knightToCast != null
                    && knightToCast.hasSubtype(SubType.KNIGHT, game)
                    && !knightToCast.isLand(game)
                    && knightToCast.isOwnedBy(source.getControllerId())
                    && game.getState().getZone(objectId) == Zone.GRAVEYARD;
        }
        return false;
    }
}
