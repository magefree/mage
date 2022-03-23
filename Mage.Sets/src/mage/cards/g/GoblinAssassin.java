package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BursegSardaukar
 */
public final class GoblinAssassin extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.GOBLIN, "Goblin");

    public GoblinAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ASSASSIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Goblin Assassin or another Goblin enters the battlefield, each player flips a coin. Each player whose coin comes up tails sacrifices a creature.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(new GoblinAssassinTriggeredEffect(), filter));
    }

    private GoblinAssassin(final GoblinAssassin card) {
        super(card);
    }

    @Override
    public GoblinAssassin copy() {
        return new GoblinAssassin(this);
    }
}

class GoblinAssassinTriggeredEffect extends OneShotEffect {

    GoblinAssassinTriggeredEffect() {
        super(Outcome.Sacrifice);
        staticText = "each player flips a coin. Each player whose coin comes up tails sacrifices a creature";
    }

    private GoblinAssassinTriggeredEffect(final GoblinAssassinTriggeredEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> perms = new ArrayList<>();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && !player.flipCoin(source, game, false)) {
                    TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
                    target.setNotTarget(true);
                    if (target.canChoose(player.getId(), source, game)) {
                        player.chooseTarget(Outcome.Sacrifice, target, source, game);
                        perms.addAll(target.getTargets());
                    }
                }
            }
            for (UUID permID : perms) {
                Permanent permanent = game.getPermanent(permID);
                if (permanent != null) {
                    permanent.sacrifice(source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public GoblinAssassinTriggeredEffect copy() {
        return new GoblinAssassinTriggeredEffect(this);
    }
}
