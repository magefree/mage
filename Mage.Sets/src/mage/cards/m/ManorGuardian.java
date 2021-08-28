package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ManorGuardian extends CardImpl {

    public ManorGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Manor Guardian dies, each player seeks a nonland card with mana value 2 or less.
        this.addAbility(new DiesSourceTriggeredAbility(new ManorGuardianEffect()));
    }

    private ManorGuardian(final ManorGuardian card) {
        super(card);
    }

    @Override
    public ManorGuardian copy() {
        return new ManorGuardian(this);
    }
}

class ManorGuardianEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterNonlandCard();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    ManorGuardianEffect() {
        super(Outcome.Benefit);
        staticText = "each player seeks a nonland card with mana value 2 or less";
    }

    private ManorGuardianEffect(final ManorGuardianEffect effect) {
        super(effect);
    }

    @Override
    public ManorGuardianEffect copy() {
        return new ManorGuardianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            player.seekCard(filter, source, game);
        }
        return true;
    }
}
