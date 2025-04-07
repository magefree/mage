package mage.cards.j;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
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

        // When Jester's Scepter enters the battlefield, exile the top five cards of target player's library face down. You may look at those cards for as long as they remain exiled.
        Ability ability = new EntersBattlefieldTriggeredAbility(new JestersScepterEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // {2}, {tap}, Put a card exiled with Jester's Scepter into its owner's graveyard: Counter target spell if it has the same name as that card.
        Ability ability2 = new SimpleActivatedAbility(new JestersScepterCounterEffect(), new ManaCostsImpl<>("{2}"));
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

    JestersScepterEffect() {
        super(Outcome.DrawCard);
        staticText = "exile the top five cards of target player's library face down";
    }

    private JestersScepterEffect(final JestersScepterEffect effect) {
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
                CardUtil.moveCardsToExileFaceDown(game, source, controller, cardsToExile, true);
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

class JestersScepterCost extends CostImpl {

    public JestersScepterCost() {
        this.text = "Put a card exiled with {this} into its owner's graveyard";
    }

    private JestersScepterCost(final JestersScepterCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            TargetCardInExile target = new TargetCardInExile(new FilterCard(), CardUtil.getCardExileZoneId(game, ability));
            target.withNotTarget(true);
            Cards cards = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, ability));
            if (cards != null
                    && !cards.isEmpty()
                    && controller.choose(Outcome.Benefit, cards, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    if (controller.moveCardToGraveyardWithInfo(card, source, game, Zone.EXILED)) {
                        if (card instanceof SplitCard) {
                            game.getState().setValue(source.getSourceId() + "_nameOfExiledCardPayment", ((SplitCard) card).getLeftHalfCard().getName());
                            game.getState().setValue(source.getSourceId() + "_nameOfExiledCardPayment2", ((SplitCard) card).getRightHalfCard().getName());
                        } else if (card instanceof ModalDoubleFacedCard) {
                            game.getState().setValue(source.getSourceId() + "_nameOfExiledCardPayment", ((ModalDoubleFacedCard) card).getLeftHalfCard().getName());
                            game.getState().setValue(source.getSourceId() + "_nameOfExiledCardPayment2", ((ModalDoubleFacedCard) card).getRightHalfCard().getName());
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

    private JestersScepterCounterEffect(final JestersScepterCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            String nameOfExiledCardPayment = (String) game.getState().getValue(source.getSourceId() + "_nameOfExiledCardPayment");
            String nameOfExiledCardPayment2 = (String) game.getState().getValue(source.getSourceId() + "_nameOfExiledCardPayment2");
            if (CardUtil.haveSameNames(spell.getCard(), nameOfExiledCardPayment, game)
                    || CardUtil.haveSameNames(spell.getCard(), nameOfExiledCardPayment2, game)) {
                return game.getStack().counter(getTargetPointer().getFirst(game, source), source, game);
            }
        }
        return false;
    }

    @Override
    public JestersScepterCounterEffect copy() {
        return new JestersScepterCounterEffect(this);
    }
}
