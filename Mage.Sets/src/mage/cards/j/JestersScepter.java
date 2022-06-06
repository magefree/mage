package mage.cards.j;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class JestersScepter extends CardImpl {

    public JestersScepter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // When Jester's Scepter enters the battlefield, exile the top five cards of target player's library face down.
        Ability ability = new EntersBattlefieldTriggeredAbility(new JestersScepterEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // You may look at those cards for as long as they remain exiled.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new JestersScepterLookAtCardEffect()));

        // {2}, {tap}, Put a card exiled with Jester's Scepter into its owner's graveyard: Counter target spell if it has the same name as that card.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JestersScepterCounterEffect(), new ManaCostsImpl<>("{2}"));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new JestersScepterCost());
        ability2.addTarget(new TargetSpell());
        this.addAbility(ability2);

    }

    private JestersScepter(final JestersScepter card) {
        super(card);
    }

    @Override
    public JestersScepter copy() {
        return new JestersScepter(this);
    }
}

class JestersScepterEffect extends OneShotEffect {

    public JestersScepterEffect() {
        super(Outcome.DrawCard);
        staticText = "exile the top five cards of target player's library face down";
    }

    public JestersScepterEffect(final JestersScepterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetedPlayer = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source);
        if (controller != null
                && targetedPlayer != null
                && sourceObject != null) {
            if (targetedPlayer.getLibrary().hasCards()) {
                Set<Card> cardsToExile = targetedPlayer.getLibrary().getTopCards(game, 5);
                for (Card card : cardsToExile) {
                    if (card.moveToExile(CardUtil.getCardExileZoneId(game, source), sourceObject.getName(), source, game)) {
                        card.setFaceDown(true, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public JestersScepterEffect copy() {
        return new JestersScepterEffect(this);
    }
}

class JestersScepterLookAtCardEffect extends AsThoughEffectImpl {

    public JestersScepterLookAtCardEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at cards exiled with {this}";
    }

    public JestersScepterLookAtCardEffect(final JestersScepterLookAtCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public JestersScepterLookAtCardEffect copy() {
        return new JestersScepterLookAtCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null) {
                MageObject sourceObject = game.getObject(source);
                if (sourceObject == null) {
                    return false;
                }
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                ExileZone exile = game.getExile().getExileZone(exileId);
                return exile != null
                        && exile.contains(objectId);
            }
        }
        return false;
    }
}

class JestersScepterCost extends CostImpl {

    public JestersScepterCost() {
        this.text = "Put a card exiled with {this} into its owner's graveyard";
    }

    public JestersScepterCost(JestersScepterCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            TargetCardInExile target = new TargetCardInExile(new FilterCard(), CardUtil.getCardExileZoneId(game, ability));
            target.setNotTarget(true);
            Cards cards = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, ability));
            if (cards != null
                    && !cards.isEmpty()
                    && controller.choose(Outcome.Benefit, cards, target, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    if (controller.moveCardToGraveyardWithInfo(card, source, game, Zone.EXILED)) {
                        if (card instanceof SplitCard) {
                            game.getState().setValue(source.getSourceId() + "_nameOfExiledCardPayment", ((SplitCard) card).getLeftHalfCard().getName());
                            game.getState().setValue(source.getSourceId() + "_nameOfExiledCardPayment2", ((SplitCard) card).getRightHalfCard().getName());
                        } else if (card instanceof ModalDoubleFacesCard) {
                            game.getState().setValue(source.getSourceId() + "_nameOfExiledCardPayment", ((ModalDoubleFacesCard) card).getLeftHalfCard().getName());
                            game.getState().setValue(source.getSourceId() + "_nameOfExiledCardPayment2", ((ModalDoubleFacesCard) card).getRightHalfCard().getName());
                        } else {
                            game.getState().setValue(source.getSourceId() + "_nameOfExiledCardPayment", card.getName());
                        }
                        paid = true;
                    }
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        return player != null;
    }

    @Override
    public JestersScepterCost copy() {
        return new JestersScepterCost(this);
    }
}

class JestersScepterCounterEffect extends OneShotEffect {

    JestersScepterCounterEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell if it has the same name as that card";
    }

    JestersScepterCounterEffect(final JestersScepterCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            String nameOfExiledCardPayment = (String) game.getState().getValue(source.getSourceId() + "_nameOfExiledCardPayment");
            String nameOfExiledCardPayment2 = (String) game.getState().getValue(source.getSourceId() + "_nameOfExiledCardPayment2");
            if (CardUtil.haveSameNames(spell.getCard(), nameOfExiledCardPayment, game)
                    || CardUtil.haveSameNames(spell.getCard(), nameOfExiledCardPayment2, game)) {
                return game.getStack().counter(targetPointer.getFirst(game, source), source, game);
            }
        }
        return false;
    }

    @Override
    public JestersScepterCounterEffect copy() {
        return new JestersScepterCounterEffect(this);
    }
}
