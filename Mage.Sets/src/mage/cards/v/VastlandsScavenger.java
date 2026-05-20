package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.RandomUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VastlandsScavenger extends PrepareCard {

    public VastlandsScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}", "Bind to Life", new CardType[]{CardType.INSTANT}, "{4}{G}");

        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // This creature enters prepared.
        // Bind to Life
        // Instant {4}{G}
        // Mill seven cards. Then put a creature card from among them onto the battlefield.
        this.getSpellCard().getSpellAbility().addEffect(new VastlandsScavengerEffect());
    }

    private VastlandsScavenger(final VastlandsScavenger card) {
        super(card);
    }

    @Override
    public VastlandsScavenger copy() {
        return new VastlandsScavenger(this);
    }
}

class VastlandsScavengerEffect extends OneShotEffect {

    VastlandsScavengerEffect() {
        super(Outcome.Benefit);
        staticText = "mill seven cards. Then put a creature card from among them onto the battlefield";
    }

    private VastlandsScavengerEffect(final VastlandsScavengerEffect effect) {
        super(effect);
    }

    @Override
    public VastlandsScavengerEffect copy() {
        return new VastlandsScavengerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Card> cards = player.millCards(7, source, game).getCards(StaticFilters.FILTER_CARD_CREATURE, game);
        Card card;
        switch (cards.size()) {
            case 0:
                return true;
            case 1:
                card = RandomUtil.randomFromCollection(cards);
                break;
            default:
                TargetCard target = new TargetCard(Zone.ALL, StaticFilters.FILTER_CARD_CREATURE);
                player.choose(outcome, new CardsImpl(cards), target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        return card == null || player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
