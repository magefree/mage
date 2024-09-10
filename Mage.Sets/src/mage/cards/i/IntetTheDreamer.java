package mage.cards.i;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.SourceRemainsInZoneCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.asthought.MayLookAtTargetCardEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class IntetTheDreamer extends CardImpl {

    protected static final String VALUE_PREFIX = "ExileZones";

    public IntetTheDreamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Intet, the Dreamer deals combat damage to a player, you may pay {2}{U}. If you do, exile the top card of your library face down. You may look at that card for as long as it remains exiled. You may play that card without paying its mana cost for as long as Intet remains on the battlefield.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoIfCostPaid(new IntetTheDreamerExileEffect(), new ManaCostsImpl<>("{2}{U}")), false, true));
    }

    private IntetTheDreamer(final IntetTheDreamer card) {
        super(card);
    }

    @Override
    public IntetTheDreamer copy() {
        return new IntetTheDreamer(this);
    }
}

class IntetTheDreamerExileEffect extends OneShotEffect {

    IntetTheDreamerExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library face down. "
                + "You may look at that card for as long as it remains exiled. "
                + "You may play that card without paying its mana cost for as long as Intet remains on the battlefield";
    }

    private IntetTheDreamerExileEffect(final IntetTheDreamerExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Card card = controller.getLibrary().getFromTop(game);
        MageObject sourceObject = source.getSourceObject(game);
        if (card == null || sourceObject == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), sourceObject.getZoneChangeCounter(game));
        String exileName = sourceObject.getIdName() + " (" + sourceObject.getZoneChangeCounter(game) + ")";
        card.setFaceDown(true, game);
        if (controller.moveCardsToExile(card, source, game, false, exileId, exileName)) {
            card.setFaceDown(true, game);
            ContinuousEffect effect = new IntetTheDreamerAsThoughEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), game.getState().getZoneChangeCounter(card.getId())));
            game.getState().addEffect(effect, source);
            effect = new MayLookAtTargetCardEffect(controller.getId());
            effect.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(effect, source);
        }
        return true;
    }

    @Override
    public IntetTheDreamerExileEffect copy() {
        return new IntetTheDreamerExileEffect(this);
    }
}

class IntetTheDreamerAsThoughEffect extends AsThoughEffectImpl {

    IntetTheDreamerAsThoughEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "You may play that card without paying its mana cost for as long as Intet remains on the battlefield.";
    }

    private IntetTheDreamerAsThoughEffect(final IntetTheDreamerAsThoughEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IntetTheDreamerAsThoughEffect copy() {
        return new IntetTheDreamerAsThoughEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        // note to always discard the effect if anything fails
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (targetId == null) {
            this.discard();
            return false;
        }
        Card card = game.getCard(objectId);
        if (card == null) {
            this.discard();
            return false;
        }

        // split cards, etc
        objectId = card.getMainCard().getId();

        if (objectId.equals(targetId)
                && affectedControllerId.equals(source.getControllerId())) {
            Card exiledCard = game.getCard(objectId);
            if (exiledCard == null) {
                this.discard();
                return false;
            }

            // cast without mana
            allowCardToPlayWithoutMana(objectId, source, affectedControllerId, game);

            // while Intet remains on battlefield
            if (!(new SourceRemainsInZoneCondition(Zone.BATTLEFIELD).apply(game, source))) {
                this.discard();
                return false;
            }
            return true;
        }
        return false;
    }
}