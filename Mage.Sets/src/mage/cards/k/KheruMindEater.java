
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public final class KheruMindEater extends CardImpl {

    public KheruMindEater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Kheru Mind-Eater deals combat damage to a player, that player exiles a card from their hand face down.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new KheruMindEaterExileEffect(), false, true));

        // You may look at and play cards exiled with Kheru Mind-Eater.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KheruMindEaterEffect()));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new KheruMindEaterLookAtCardEffect()));
    }

    private KheruMindEater(final KheruMindEater card) {
        super(card);
    }

    @Override
    public KheruMindEater copy() {
        return new KheruMindEater(this);
    }
}

class KheruMindEaterExileEffect extends OneShotEffect {

    public KheruMindEaterExileEffect() {
        super(Outcome.Discard);
        staticText = "that player exiles a card of their hand face down";
    }

    public KheruMindEaterExileEffect(final KheruMindEaterExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null && !player.getHand().isEmpty()) {
            Target target = new TargetCardInHand(1, new FilterCard());
            target.chooseTarget(Outcome.Exile, player.getId(), source, game);
            Card card = game.getCard(target.getFirstTarget());
            MageObject sourceObject = game.getObject(source.getSourceId());
            if (card != null && sourceObject != null) {
                if (player.moveCardsToExile(card, source, game, false, CardUtil.getCardExileZoneId(game, source), sourceObject.getIdName())) {
                    card.setFaceDown(true, game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public KheruMindEaterExileEffect copy() {
        return new KheruMindEaterExileEffect(this);
    }
}

class KheruMindEaterEffect extends AsThoughEffectImpl {

    public KheruMindEaterEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may play cards exiled with {this}";
    }

    public KheruMindEaterEffect(final KheruMindEaterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public KheruMindEaterEffect copy() {
        return new KheruMindEaterEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(objectId);
        if (affectedControllerId.equals(source.getControllerId()) && card != null && game.getState().getZone(card.getId()) == Zone.EXILED) {
            ExileZone zone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
            return zone != null && zone.contains(card.getId());
        }
        return false;
    }
}

class KheruMindEaterLookAtCardEffect extends AsThoughEffectImpl {

    public KheruMindEaterLookAtCardEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at cards exiled with {this}";
    }

    public KheruMindEaterLookAtCardEffect(final KheruMindEaterLookAtCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public KheruMindEaterLookAtCardEffect copy() {
        return new KheruMindEaterLookAtCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null) {
                MageObject sourceObject = game.getObject(source.getSourceId());
                if (sourceObject == null) {
                    return false;
                }
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                ExileZone exile = game.getExile().getExileZone(exileId);
                return exile != null && exile.contains(objectId);
            }
        }
        return false;
    }

}
