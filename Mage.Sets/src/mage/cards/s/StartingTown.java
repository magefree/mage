package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StartingTown extends CardImpl {

    public StartingTown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.TOWN);

        // This land enters tapped unless it's your first, second, or third turn of the game.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(StartingTownCondition.instance));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Pay 1 life: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private StartingTown(final StartingTown card) {
        super(card);
    }

    @Override
    public StartingTown copy() {
        return new StartingTown(this);
    }
}

enum StartingTownCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.isActivePlayer(source.getControllerId())
                && Optional
                .ofNullable(source.getControllerId())
                .map(game::getPlayer)
                .map(Player::getTurns)
                .map(x -> x <= 3)
                .orElse(false);
    }

    @Override
    public String toString() {
        return "it's your first, second, or third turn of the game";
    }
}
