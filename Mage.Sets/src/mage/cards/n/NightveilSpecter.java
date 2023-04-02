
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * FAQ
 *
 * The card is exiled face up. All players may look at it. Playing a card exiled
 * with Nightveil Specter follows all the normal rules for playing that card.
 * You must pay its costs, and you must follow all timing restrictions, for
 * example.
 *
 * Nightveil Specter's last ability applies to cards exiled with that specific
 * Nightveil Specter, not any other creature named Nightveil Specter. You should
 * keep cards exiled by different Nightveil Specters separate.
 *
 * @author LevelX2
 */
public final class NightveilSpecter extends CardImpl {

    public NightveilSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U/B}{U/B}{U/B}");
        this.subtype.add(SubType.SPECTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Nightveil Specter deals combat damage to a player, that player exiles the top card of their library.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new NightveilSpecterExileEffect(), false, true));

        // You may play cards exiled with Nightveil Specter.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NightveilSpecterEffect()));
    }

    private NightveilSpecter(final NightveilSpecter card) {
        super(card);
    }

    @Override
    public NightveilSpecter copy() {
        return new NightveilSpecter(this);
    }
}

class NightveilSpecterExileEffect extends OneShotEffect {

    public NightveilSpecterExileEffect() {
        super(Outcome.Discard);
        staticText = "that player exiles the top card of their library";
    }

    public NightveilSpecterExileEffect(final NightveilSpecterExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                player.moveCardsToExile(card, source, game, true, CardUtil.getCardExileZoneId(game, source), CardUtil.createObjectRealtedWindowTitle(source, game, null));
                return true;
            }
        }
        return false;
    }

    @Override
    public NightveilSpecterExileEffect copy() {
        return new NightveilSpecterExileEffect(this);
    }
}

class NightveilSpecterEffect extends AsThoughEffectImpl {

    public NightveilSpecterEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may play cards exiled with {this}";
    }

    public NightveilSpecterEffect(final NightveilSpecterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public NightveilSpecterEffect copy() {
        return new NightveilSpecterEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card theCard = game.getCard(objectId);
        if (theCard == null) {
            return false;
        }
        objectId = theCard.getMainCard().getId();// for split cards

        if (affectedControllerId.equals(source.getControllerId()) && game.getState().getZone(objectId) == Zone.EXILED) {
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
            return exileZone != null && exileZone.contains(objectId);
        }
        return false;
    }
}
