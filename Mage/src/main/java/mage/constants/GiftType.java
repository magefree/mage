package mage.constants;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.token.FishNoAbilityToken;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.OctopusToken;
import mage.game.permanent.token.TreasureToken;
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
 * @author TheElk801
 */
public enum GiftType {
    CARD(
            "a card", "draw a card",
            (p, g, s) -> p.drawCards(1, s, g) > 0
    ),
    FOOD(
            "a Food", "create a Food token",
            (p, g, s) -> new FoodToken().putOntoBattlefield(1, g, s, p.getId())
    ),
    TREASURE(
            "a Treasure", "create a Treasure token",
            (p, g, s) -> new TreasureToken().putOntoBattlefield(1, g, s, p.getId())
    ),
    TAPPED_FISH(
            "a tapped Fish", "create a tapped 1/1 blue Fish creature token",
            (p, g, s) -> new FishNoAbilityToken().putOntoBattlefield(1, g, s, p.getId(), true, false)
    ),
    OCTOPUS(
            "an Octopus", "they create an 8/8 blue Octopus creature token",
            (p, g, s) -> new OctopusToken().putOntoBattlefield(1, g, s, p.getId())
    ),
    EXTRA_TURN(
            "an extra turn", "take an extra turn after this one",
            (p, g, s) -> g.getState().getTurnMods().add(new TurnMod(p.getId()).withExtraTurn())
    );

    private interface GiftResolver {
        boolean apply(Player player, Game game, Ability source);
    }

    private final String name;
    private final String description;
    private final GiftResolver giftResolver;

    GiftType(String name, String description, GiftResolver giftResolver) {
        this.name = name;
        this.description = description;
        this.giftResolver = giftResolver;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean applyGift(Player player, Game game, Ability source) {
        return giftResolver.apply(player, game, source);
    }
}
