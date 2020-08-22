package mage.cards.n;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NivMizzetReborn extends CardImpl {

    public NivMizzetReborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Niv-Mizzet Reborn enters the battlefield, reveal the top ten cards of your library. For each color pair, choose a card that's exactly those colors from among them. Put the chosen cards into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new NivMizzetRebornEffect()));
    }

    private NivMizzetReborn(final NivMizzetReborn card) {
        super(card);
    }

    @Override
    public NivMizzetReborn copy() {
        return new NivMizzetReborn(this);
    }
}

class NivMizzetRebornEffect extends OneShotEffect {

    private static enum Guild {
        G0("W", "U"), G1("W", "B"), G2("U", "B"), G3("U", "R"), G4("B", "R"),
        G5("B", "G"), G6("R", "G"), G7("R", "W"), G8("G", "W"), G9("G", "U");

        private static final Map<String, String> nameMap = new HashMap();

        static {
            nameMap.put("W", "white");
            nameMap.put("U", "blue");
            nameMap.put("B", "black");
            nameMap.put("R", "red");
            nameMap.put("G", "green");
        }

        private final String color1;
        private final String color2;

        private Guild(String color1, String color2) {
            this.color1 = color1;
            this.color2 = color2;
        }

        private FilterCard makeFilter() {
            FilterCard filter = new FilterCard(getDescription());
            filter.add(new ColorPredicate(new ObjectColor(color1)));
            filter.add(new ColorPredicate(new ObjectColor(color2)));
            for (char c : getOtherColors().toCharArray()) {
                filter.add(Predicates.not(new ColorPredicate(new ObjectColor("" + c))));
            }
            return filter;
        }

        private TargetCard getTarget() {
            return new TargetCard(Zone.LIBRARY, makeFilter());
        }

        private String getDescription() {
            return "card that is exactly " + nameMap.get(color1) + " and " + nameMap.get(color2);
        }

        private String getOtherColors() {
            String colors = color1 + color2;
            String otherColors = "";
            for (char c : "WUBRG".toCharArray()) {
                if (color1.charAt(0) == c || color2.charAt(0) == c) {
                    continue;
                }
                otherColors += c;
            }
            return otherColors;
        }

        private boolean isInCards(Cards cards, Game game) {
            FilterCard filter = makeFilter();
            return cards.getCards(game).stream().anyMatch(card -> filter.match(card, game));
        }
    }

    NivMizzetRebornEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top ten cards of your library. For each color pair, "
                + "choose a card that's exactly those colors from among them. "
                + "Put the chosen cards into your hand and the rest on the bottom of your library in a random order.";
    }

    private NivMizzetRebornEffect(final NivMizzetRebornEffect effect) {
        super(effect);
    }

    @Override
    public NivMizzetRebornEffect copy() {
        return new NivMizzetRebornEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 10));
        game.informPlayers(player.getName() + " reveals " +
                cards.getCards(game).stream().map(card -> card.getName() + " ").reduce((a, b) -> a + b));
        Cards cards2 = new CardsImpl();
        if (cards.isEmpty()) {
            return false;
        }
        player.revealCards(source, cards, game);
        for (Guild guild : Guild.values()) {
            if (!guild.isInCards(cards, game)) {
                continue;
            }
            TargetCard target = guild.getTarget();
            if (player.choose(outcome, cards, target, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    cards2.add(card);
                }
            }
        }
        cards.removeAll(cards2);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        if (player.moveCards(cards2, Zone.HAND, source, game)) {
            for (Card card : cards2.getCards(game)) {
                game.informPlayers(player.getName() + " chose " + card.getName() + " and put it into their hand.");
            }
        }
        return true;
    }
}
// I think this is my favorite card I've ever implemented
