package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HeadlinerScarlett extends CardImpl {

    public HeadlinerScarlett(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Headliner Scarlett enters the battlefield, creatures target player controls can't block this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new HeadlinerScarlettEntersEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // At the beginning of your upkeep, exile the top card of your library face down. You may look at and play that card this turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new HeadlinerScarlettExileEffect(), TargetController.YOU, false));
    }

    private HeadlinerScarlett(final HeadlinerScarlett card) {
        super(card);
    }

    @Override
    public HeadlinerScarlett copy() {
        return new HeadlinerScarlett(this);
    }
}

class HeadlinerScarlettEntersEffect extends OneShotEffect {

    HeadlinerScarlettEntersEffect() {
        super(Outcome.Detriment);
        staticText = "creatures target player controls can't block this turn";
    }

    private HeadlinerScarlettEntersEffect(final HeadlinerScarlettEntersEffect effect) {
        super(effect);
    }

    @Override
    public HeadlinerScarlettEntersEffect copy() {
        return new HeadlinerScarlettEntersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures target player controls");
        filter.add(new ControllerIdPredicate(source.getFirstTarget()));
        game.addEffect(new CantBlockAllEffect(filter, Duration.EndOfTurn), source);
        return true;
    }
}

class HeadlinerScarlettExileEffect extends OneShotEffect {

    HeadlinerScarlettExileEffect() {
        super(Outcome.DrawCard);
        staticText = "exile the top card of your library face down. "
                + "You may look at and play that card this turn.";
    }

    private HeadlinerScarlettExileEffect(final HeadlinerScarlettExileEffect effect) {
        super(effect);
    }

    @Override
    public HeadlinerScarlettExileEffect copy() {
        return new HeadlinerScarlettExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }

        // 1 exile zone per turn
        UUID exileId = CardUtil.getExileZoneId("HeadlinerScarlett::" + source.getSourceId() + "::" + game.getTurn(), game);
        String exileName = CardUtil.getSourceIdName(game, source) + " turn:" + game.getTurnNum();

        card.setFaceDown(true, game);
        controller.moveCardsToExile(card, source, game, false, exileId, exileName);
        if (game.getState().getZone(card.getId()) == Zone.EXILED) {
            card.setFaceDown(true, game);
            CardUtil.makeCardPlayable(game, source, card, false, Duration.EndOfTurn, false);
            ContinuousEffect effect = new HeadlinerScarlettLookEffect(controller.getId());
            effect.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(effect, source);
        }
        return true;
    }

}

class HeadlinerScarlettLookEffect extends AsThoughEffectImpl {

    private final UUID authorizedPlayerId;

    public HeadlinerScarlettLookEffect(UUID authorizedPlayerId) {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        this.authorizedPlayerId = authorizedPlayerId;
    }

    private HeadlinerScarlettLookEffect(final HeadlinerScarlettLookEffect effect) {
        super(effect);
        this.authorizedPlayerId = effect.authorizedPlayerId;
    }

    @Override
    public HeadlinerScarlettLookEffect copy() {
        return new HeadlinerScarlettLookEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID cardId = getTargetPointer().getFirst(game, source);
        if (cardId == null) {
            this.discard(); // card is no longer in the origin zone, effect can be discarded
        }
        return affectedControllerId.equals(authorizedPlayerId)
                && objectId.equals(cardId);
    }
}
