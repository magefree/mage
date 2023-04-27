package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.NecronWarriorToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NecronMonolith extends CardImpl {

    public NecronMonolith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Eternity Gate -- Whenever Necron Monolith attacks, mill three cards. For each creature card milled this way, create a 2/2 black Necron Warrior artifact creature token.
        this.addAbility(new AttacksTriggeredAbility(new NecronMonolithEffect()).withFlavorWord("Eternity Gate"));

        // Crew 4
        this.addAbility(new CrewAbility(4));
    }

    private NecronMonolith(final NecronMonolith card) {
        super(card);
    }

    @Override
    public NecronMonolith copy() {
        return new NecronMonolith(this);
    }
}

class NecronMonolithEffect extends OneShotEffect {

    NecronMonolithEffect() {
        super(Outcome.Benefit);
        staticText = "mill three cards. For each creature card milled this way, " +
                "create a 2/2 black Necron Warrior artifact creature token";
    }

    private NecronMonolithEffect(final NecronMonolithEffect effect) {
        super(effect);
    }

    @Override
    public NecronMonolithEffect copy() {
        return new NecronMonolithEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = player
                .millCards(3, source, game)
                .count(StaticFilters.FILTER_CARD_CREATURE, game);
        return count > 0 && new NecronWarriorToken().putOntoBattlefield(count, game, source);
    }
}
