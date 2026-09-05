package mage.cards.l;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MountaincyclingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author muz
 */
public final class LastLightOfDurinsDay extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.MOUNTAIN, "a Mountain");

    public LastLightOfDurinsDay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Whenever a Mountain you control enters, put a quest counter on this enchantment.
        // If it has six or more quest counters on it, sacrifice it.
        // If you do, search your hand and/or library for a Dragon card and put it onto the battlefield. If you search your library this way, shuffle.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
            new AddCountersSourceEffect(CounterType.QUEST.createInstance()),
            filter
        );
        ability.addEffect(new LastLightOfDurinsDayEffect());
        this.addAbility(ability);

        // Mountaincycling {2}
        this.addAbility(new MountaincyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private LastLightOfDurinsDay(final LastLightOfDurinsDay card) {
        super(card);
    }

    @Override
    public LastLightOfDurinsDay copy() {
        return new LastLightOfDurinsDay(this);
    }
}

class LastLightOfDurinsDayEffect extends OneShotEffect {

    public LastLightOfDurinsDayEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "If it has six or more quest counters on it, sacrifice it. If you do, search your hand and/or library for a Dragon card and put it onto the battlefield. If you search your library this way, shuffle.";
    }

    private LastLightOfDurinsDayEffect(final LastLightOfDurinsDayEffect effect) {
        super(effect);
    }

    @Override
    public LastLightOfDurinsDayEffect copy() {
        return new LastLightOfDurinsDayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }

        if (permanent.getCounters(game).getCount(CounterType.QUEST) < 6 || !permanent.sacrifice(source, game)) {
            return true;
        }

        FilterCard filter = new FilterCard(SubType.DRAGON);
        Card card = null;

        // Choose a card from your hand
        if (player.chooseUse(Outcome.Neutral, "Search your hand for a Dragon card?", source, game)) {
            TargetCardInHand target = new TargetCardInHand(filter);
            if (player.choose(Outcome.PutCardInPlay, player.getHand(), target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }

        // Choose a card from your library
        if (card == null && player.chooseUse(Outcome.Neutral, "Search your library for a Dragon card?", source, game)) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (player.searchLibrary(target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
            player.shuffleLibrary(source, game);
        }

        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}
