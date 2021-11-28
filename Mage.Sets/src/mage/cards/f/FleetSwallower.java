
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class FleetSwallower extends CardImpl {

    public FleetSwallower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Fleet Swallower attacks, target player puts the top half of their library, rounded up, into their graveyard.
        Ability ability = new AttacksTriggeredAbility(new FleetSwallowerEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private FleetSwallower(final FleetSwallower card) {
        super(card);
    }

    @Override
    public FleetSwallower copy() {
        return new FleetSwallower(this);
    }
}

class FleetSwallowerEffect extends OneShotEffect {

    public FleetSwallowerEffect() {
        super(Outcome.Detriment);
        staticText = "target player mills half their library, rounded up";
    }

    public FleetSwallowerEffect(final FleetSwallowerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            int amount = (int) Math.ceil(player.getLibrary().size() * .5);
            player.millCards(amount, source, game);
            return true;
        }
        return false;
    }

    @Override
    public FleetSwallowerEffect copy() {
        return new FleetSwallowerEffect(this);
    }

}
