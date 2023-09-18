package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaywardGuideBeast extends CardImpl {

    public WaywardGuideBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Wayward Guide-Beast deals combat damage to a player, return a land you control to its owner's hand.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new WaywardGuideBeastEffect(), false));
    }

    private WaywardGuideBeast(final WaywardGuideBeast card) {
        super(card);
    }

    @Override
    public WaywardGuideBeast copy() {
        return new WaywardGuideBeast(this);
    }
}

class WaywardGuideBeastEffect extends OneShotEffect {

    WaywardGuideBeastEffect() {
        super(Outcome.Benefit);
        staticText = "return a land you control to its owner's hand";
    }

    private WaywardGuideBeastEffect(final WaywardGuideBeastEffect effect) {
        super(effect);
    }

    @Override
    public WaywardGuideBeastEffect copy() {
        return new WaywardGuideBeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                source.getControllerId(), source, game
        ) == 0) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                1, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, true
        );
        player.choose(outcome, target, source, game);
        player.moveCards(game.getCard(target.getFirstTarget()), Zone.HAND, source, game);
        return true;
    }
}
