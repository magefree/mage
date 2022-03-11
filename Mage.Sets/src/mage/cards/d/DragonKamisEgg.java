package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonKamisEgg extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DRAGON);

    public DragonKamisEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.EGG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);
        this.color.setGreen(true);
        this.nightCard = true;

        // Whenever Dragon-Kami's Egg or a Dragon you control dies, you may cast a creature spell from among cards you own in exile with hatching counters on them without paying its mana cost.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(
                new DragonKamisEggEffect(), false, filter
        ).setTriggerPhrase("Whenever {this} or a Dragon you control dies, "));
    }

    private DragonKamisEgg(final DragonKamisEgg card) {
        super(card);
    }

    @Override
    public DragonKamisEgg copy() {
        return new DragonKamisEgg(this);
    }
}

class DragonKamisEggEffect extends OneShotEffect {

    DragonKamisEggEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast a creature spell from among cards you own in exile " +
                "with hatching counters on them without paying its mana cost";
    }

    private DragonKamisEggEffect(final DragonKamisEggEffect effect) {
        super(effect);
    }

    @Override
    public DragonKamisEggEffect copy() {
        return new DragonKamisEggEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getExile()
                .getAllCards(game, player.getId())
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> card.getCounters(game).containsKey(CounterType.HATCHLING))
                .forEach(cards::add);
        return !cards.isEmpty() && CardUtil.castSpellWithAttributesForFree(
                player, source, game, cards, StaticFilters.FILTER_CARD_CREATURE
        );
    }
}
