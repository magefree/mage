package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AraumiOfTheDeadTide extends CardImpl {

    public AraumiOfTheDeadTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {T}, Exile cards from your graveyard equal to the number of opponents you have: Target creature card in your graveyard gains encore until end of turn. The encore cost is equal to its mana cost.
        Ability ability = new SimpleActivatedAbility(new AraumiOfTheDeadTideEffect(), new TapSourceCost());
        ability.addCost(new AraumiOfTheDeadTideCost());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private AraumiOfTheDeadTide(final AraumiOfTheDeadTide card) {
        super(card);
    }

    @Override
    public AraumiOfTheDeadTide copy() {
        return new AraumiOfTheDeadTide(this);
    }
}

class AraumiOfTheDeadTideEffect extends ContinuousEffectImpl {

    AraumiOfTheDeadTideEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Target creature card in your graveyard gains encore until end of turn. " +
                "The encore cost is equal to its mana cost.";
    }

    private AraumiOfTheDeadTideEffect(final AraumiOfTheDeadTideEffect effect) {
        super(effect);
    }

    @Override
    public AraumiOfTheDeadTideEffect copy() {
        return new AraumiOfTheDeadTideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card == null) {
            return false;
        }
        game.getState().addOtherAbility(card, new EncoreAbility(card.getManaCost()));
        return true;
    }
}

class AraumiOfTheDeadTideCost extends CostImpl {

    AraumiOfTheDeadTideCost() {
        this.text = "exile cards from your graveyard equal to the number of opponents you have";
    }

    private AraumiOfTheDeadTideCost(final AraumiOfTheDeadTideCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return paid;
        }
        int oppCount = game.getOpponents(controllerId).size();
        TargetCard target = new TargetCardInYourGraveyard(oppCount, StaticFilters.FILTER_CARD);
        target.setNotTarget(true);
        player.choose(Outcome.Exile, target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        if (cards.size() < oppCount) {
            return paid;
        }
        player.moveCards(cards, Zone.EXILED, ability, game);
        paid = cards
                .stream()
                .map(game.getState()::getZone)
                .filter(Zone.EXILED::equals)
                .count() >= oppCount;
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        return player != null && player.getGraveyard().size() >= game.getOpponents(controllerId).size();
    }

    @Override
    public AraumiOfTheDeadTideCost copy() {
        return new AraumiOfTheDeadTideCost(this);
    }
}
