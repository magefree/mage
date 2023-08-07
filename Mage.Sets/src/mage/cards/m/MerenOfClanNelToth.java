package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MerenOfClanNelToth extends CardImpl {

    public MerenOfClanNelToth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever another creature you control dies, you get an experience counter.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersPlayersEffect(
                CounterType.EXPERIENCE.createInstance(), TargetController.YOU
        ), false, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL));

        // At the beginning of your end step, choose target creature card in your graveyard. 
        // If that card's converted mana cost is less than or equal to the number of experience counters you have, return it to the battlefield. Otherwise, put it into your hand.
        Ability ability = new BeginningOfYourEndStepTriggeredAbility(new MerenOfClanNelTothEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private MerenOfClanNelToth(final MerenOfClanNelToth card) {
        super(card);
    }

    @Override
    public MerenOfClanNelToth copy() {
        return new MerenOfClanNelToth(this);
    }
}

class MerenOfClanNelTothEffect extends OneShotEffect {

    MerenOfClanNelTothEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "choose target creature card in your graveyard. If that card's mana value " +
                "is less than or equal to the number of experience counters you have, " +
                "return it to the battlefield. Otherwise, put it into your hand";
    }

    private MerenOfClanNelTothEffect(final MerenOfClanNelTothEffect effect) {
        super(effect);
    }

    @Override
    public MerenOfClanNelTothEffect copy() {
        return new MerenOfClanNelTothEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        boolean flag = card.getManaValue() <= player.getCounters().getCount(CounterType.EXPERIENCE);
        return player.moveCards(card, flag ? Zone.BATTLEFIELD : Zone.HAND, source, game);
    }
}
