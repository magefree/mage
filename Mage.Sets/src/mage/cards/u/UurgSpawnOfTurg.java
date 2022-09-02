package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UurgSpawnOfTurg extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_LAND);

    public UurgSpawnOfTurg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Uurg, Spawn of Turg's power is equal to the number of land cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(xValue, Duration.EndOfGame)));

        // At the beginning of your upkeep, look at the top card of your library. You may put that card into your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new UurgSpawnOfTurgEffect(), TargetController.YOU, false
        ));

        // {B}{G}, Sacrifice a land: You gain 2 life.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(2), new ManaCostsImpl<>("{B}{G}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT));
        this.addAbility(ability);
    }

    private UurgSpawnOfTurg(final UurgSpawnOfTurg card) {
        super(card);
    }

    @Override
    public UurgSpawnOfTurg copy() {
        return new UurgSpawnOfTurg(this);
    }
}

class UurgSpawnOfTurgEffect extends OneShotEffect {

    UurgSpawnOfTurgEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. You may put that card into your graveyard";
    }

    private UurgSpawnOfTurgEffect(final UurgSpawnOfTurgEffect effect) {
        super(effect);
    }

    @Override
    public UurgSpawnOfTurgEffect copy() {
        return new UurgSpawnOfTurgEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card topCard = controller.getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }
        controller.lookAtCards("Top card of your library", topCard, game);
        if (controller.chooseUse(Outcome.AIDontUseIt, "Put the top card of your library into your graveyard?", source, game)) {
            controller.moveCards(topCard, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}
