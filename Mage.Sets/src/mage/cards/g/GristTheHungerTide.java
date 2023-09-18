package mage.cards.g;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.IzoniInsectToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GristTheHungerTide extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURES);

    public GristTheHungerTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GRIST);
        this.setStartingLoyalty(3);

        // As long as Grist, the Hunger Tide isn't on the battlefield, it's a 1/1 Insect creature in addition to its other types.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new GristTheHungerTideTypeEffect()));

        // +1: Create a 1/1 black and green Insect creature token, then mill a card. If an Insect card was milled this way, put a loyalty counter on Grist and repeat this process.
        this.addAbility(new LoyaltyAbility(new GristTheHungerTideTokenEffect(), 1));

        // −2: You may sacrifice a creature. When you do, destroy target creature or planeswalker.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DestroyTargetEffect(), false, "destroy target creature or planeswalker"
        );
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(new LoyaltyAbility(new DoWhenCostPaid(
                ability,
                new SacrificeTargetCost(new TargetControlledPermanent(
                        StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
                )), "Sacrifice a creature?"
        ), -2));

        // −5: Each opponent loses life equal to the number of creature cards in your graveyard.
        this.addAbility(new LoyaltyAbility(new LoseLifeOpponentsEffect(xValue).setText("each opponent loses life equal to the number of creature cards in your graveyard"), -5));
    }

    private GristTheHungerTide(final GristTheHungerTide card) {
        super(card);
    }

    @Override
    public GristTheHungerTide copy() {
        return new GristTheHungerTide(this);
    }

    @Override
    public List<CardType> getCardTypeForDeckbuilding() {
        return Arrays.asList(CardType.PLANESWALKER, CardType.CREATURE);
    }

    @Override
    public boolean hasSubTypeForDeckbuilding(SubType subType) {
        return subType == SubType.INSECT || super.hasSubTypeForDeckbuilding(subType);
    }
}

class GristTheHungerTideTypeEffect extends ContinuousEffectImpl {

    GristTheHungerTideTypeEffect() {
        super(Duration.Custom, Outcome.Benefit);
        staticText = "as long as {this} isn't on the battlefield, " +
                "it's a 1/1 Insect creature in addition to its other types";
    }

    private GristTheHungerTideTypeEffect(final GristTheHungerTideTypeEffect effect) {
        super(effect);
    }

    @Override
    public GristTheHungerTideTypeEffect copy() {
        return new GristTheHungerTideTypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (game.getState().getZone(source.getSourceId()) == Zone.BATTLEFIELD) {
            return false;
        }
        MageObject sourceObject = game.getCard(source.getSourceId());
        if (sourceObject == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                sourceObject.addCardType(game, CardType.CREATURE);
                sourceObject.addSubType(game, SubType.INSECT);
                break;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    sourceObject.getPower().setModifiedBaseValue(1);
                    sourceObject.getToughness().setModifiedBaseValue(1);
                }
                break;
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.PTChangingEffects_7;
    }
}

class GristTheHungerTideTokenEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(SubType.INSECT.getPredicate());
    }

    GristTheHungerTideTokenEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 black and green Insect creature token, then mill a card. " +
                "If an Insect card was milled this way, put a loyalty counter on {this} and repeat this process";
    }

    private GristTheHungerTideTokenEffect(final GristTheHungerTideTokenEffect effect) {
        super(effect);
    }

    @Override
    public GristTheHungerTideTokenEffect copy() {
        return new GristTheHungerTideTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Token token = new IzoniInsectToken();
        while (true) {
            token.putOntoBattlefield(1, game, source, source.getControllerId());
            if (player.millCards(1, source, game).count(filter, game) < 1) {
                break;
            }
            if (permanent != null) {
                permanent.addCounters(CounterType.LOYALTY.createInstance(), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}
