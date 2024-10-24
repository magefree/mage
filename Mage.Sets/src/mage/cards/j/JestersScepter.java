package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.Optional;
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
        ability = new SimpleActivatedAbility(new JestersScepterCounterEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new JestersScepterCost());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
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
        Player targetedPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || targetedPlayer == null) {
            return false;
        }
        Cards cards = new CardsImpl(targetedPlayer.getLibrary().getTopCards(game, 5));
        controller.moveCardsToExile(
                cards.getCards(game), source, game, false,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        cards.retainZone(Zone.EXILED, game);
        for (Card card : cards.getCards(game)) {
            card.setFaceDown(true, game);
        }
        game.addEffect(new JestersScepterLookAtCardEffect().setTargetPointer(new FixedTargets(cards, game)), source);
        return true;
    }

    @Override
    public JestersScepterEffect copy() {
        return new JestersScepterEffect(this);
    }
}

class JestersScepterLookAtCardEffect extends AsThoughEffectImpl {

    JestersScepterLookAtCardEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may look at cards exiled with {this}";
    }

    private JestersScepterLookAtCardEffect(final JestersScepterLookAtCardEffect effect) {
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
        return objectId != null
                && source.isControlledBy(affectedControllerId)
                && this.getTargetPointer().getTargets(game, source).contains(objectId);
    }
}

class JestersScepterCost extends CostImpl {

    JestersScepterCost() {
        this.text = "Put a card exiled with {this} into its owner's graveyard";
    }

    private JestersScepterCost(final JestersScepterCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return paid;
        }
        TargetCardInExile target = new TargetCardInExile(
                0, 1, StaticFilters.FILTER_CARD, CardUtil.getExileZoneId(game, source)
        );
        target.withNotTarget(true);
        controller.choose(Outcome.Neutral, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return paid;
        }
        controller.moveCards(card, Zone.GRAVEYARD, source, game);
        source.getEffects().setValue("graveyardCard", card);
        paid = true;
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return Optional
                .ofNullable(CardUtil.getExileZoneId(game, source))
                .map(game.getExile()::getExileZone)
                .map(e -> !e.isEmpty())
                .orElse(false);
    }

    @Override
    public JestersScepterCost copy() {
        return new JestersScepterCost(this);
    }
}

class JestersScepterCounterEffect extends OneShotEffect {

    JestersScepterCounterEffect() {
        super(Outcome.Detriment);
        staticText = "counter target spell if it has the same name as that card";
    }

    private JestersScepterCounterEffect(final JestersScepterCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        Card card = (Card) getValue("graveyardCard");
        return spell != null
                && card != null
                && spell.sharesName(card, game)
                && game.getStack().counter(getTargetPointer().getFirst(game, source), source, game);
    }

    @Override
    public JestersScepterCounterEffect copy() {
        return new JestersScepterCounterEffect(this);
    }
}
