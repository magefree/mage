
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CountrysideCrusher extends CardImpl {

    public CountrysideCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, reveal the top card of your library. If it's a land card, put it into your graveyard and repeat this process.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CountrysideCrusherEffect(), TargetController.YOU, false
        ));

        // Whenever a land card is put into your graveyard from anywhere, put a +1/+1 counter on Countryside Crusher.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, StaticFilters.FILTER_CARD_LAND_A, TargetController.YOU
        ));

    }

    private CountrysideCrusher(final CountrysideCrusher card) {
        super(card);
    }

    @Override
    public CountrysideCrusher copy() {
        return new CountrysideCrusher(this);
    }
}

class CountrysideCrusherEffect extends OneShotEffect {

    CountrysideCrusherEffect() {
        super(Outcome.Discard);
        this.staticText = "reveal the top card of your library. If it's a land card, put it into your graveyard and repeat this process";
    }

    private CountrysideCrusherEffect(final CountrysideCrusherEffect effect) {
        super(effect);
    }

    @Override
    public CountrysideCrusherEffect copy() {
        return new CountrysideCrusherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller == null || sourcePermanent == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (Card card : controller.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.isLand(game)) {
                controller.moveCards(card, Zone.GRAVEYARD, source, game);
            } else {
                break;
            }
        }
        controller.revealCards(sourcePermanent.getName(), cards, game);
        return true;
    }
}
