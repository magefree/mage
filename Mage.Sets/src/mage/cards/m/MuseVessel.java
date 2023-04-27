package mage.cards.m;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

/**
 *
 * @author noahg
 */
public final class MuseVessel extends CardImpl {

    public MuseVessel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        

        // {3}, {tap}: Target player exiles a card from their hand. Activate this ability only any time you could cast a sorcery.
        ActivateAsSorceryActivatedAbility tapAbility = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new MuseVesselExileEffect(), new TapSourceCost());
        tapAbility.addCost(new ManaCostsImpl<>("{3}"));
        tapAbility.addTarget(new TargetPlayer());
        this.addAbility(tapAbility);

        // {1}: Choose a card exiled with Muse Vessel. You may play that card this turn.
        SimpleActivatedAbility playAbility = new SimpleActivatedAbility(new MuseVesselMayPlayExiledEffect(), new ManaCostsImpl<>("{1}"));
        playAbility.addTarget(new TargetCardInMuseVesselExile());
        this.addAbility(playAbility);
    }

    private MuseVessel(final MuseVessel card) {
        super(card);
    }

    @Override
    public MuseVessel copy() {
        return new MuseVessel(this);
    }
}

class MuseVesselExileEffect extends OneShotEffect {

    public MuseVesselExileEffect() {
        super(Outcome.Exile);
        staticText = "target player exiles a card from their hand";
    }

    public MuseVesselExileEffect(final MuseVesselExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }
        if (player == null) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand();
        if (target.canChoose(player.getId(), source, game)
                && target.chooseTarget(Outcome.Exile, player.getId(), source, game)) {
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            return player.moveCardsToExile(new CardsImpl(target.getTargets()).getCards(game), source, game, true, exileId, sourceObject.getIdName());
        }
        return false;
    }

    @Override
    public MuseVesselExileEffect copy() {
        return new MuseVesselExileEffect(this);
    }

}

class MuseVesselMayPlayExiledEffect extends AsThoughEffectImpl {

    public MuseVesselMayPlayExiledEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        this.staticText = "Choose a card exiled with {this}. You may play that card this turn";
    }

    public MuseVesselMayPlayExiledEffect(final MuseVesselMayPlayExiledEffect effect) {
        super(effect);
    }

    @Override
    public MuseVesselMayPlayExiledEffect copy() {
        return new MuseVesselMayPlayExiledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return affectedControllerId.equals(source.getControllerId())
                && getTargetPointer().getTargets(game, source).contains(objectId);
    }

}

class TargetCardInMuseVesselExile extends TargetCardInExile {

    public TargetCardInMuseVesselExile() {
        super(1, 1, new FilterCard("card exiled with Muse Vessel"), null);
    }

    public TargetCardInMuseVesselExile(final TargetCardInMuseVesselExile target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard != null) {
            UUID exileId = CardUtil.getCardExileZoneId(game, source.getSourceId());
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null && !exile.isEmpty()) {
                possibleTargets.addAll(exile);
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard != null) {
            UUID exileId = CardUtil.getCardExileZoneId(game, source.getSourceId());
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null && !exile.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Zone.EXILED) {
            ExileZone exile = null;
            Card sourceCard = game.getCard(source.getSourceId());
            if (sourceCard != null) {
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                exile = game.getExile().getExileZone(exileId);
            }
            if (exile != null && exile.contains(id)) {
                return filter.match(card, source.getControllerId(), game);
            }
        }
        return false;
    }

    @Override
    public TargetCardInMuseVesselExile copy() {
        return new TargetCardInMuseVesselExile(this);
    }
}