
package mage.cards.k;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterOpponent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author stevemarkham81
 */
public final class KeeperOfTheLight extends CardImpl {

    public KeeperOfTheLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {W}, {T}: Choose target opponent who had more life than you did as you activated this ability. You gain 3 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainLifeEffect(3).setText("Choose target opponent who had more life than you did as you activated this ability. You gain 3 life."),
                new ManaCostsImpl("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new KeeperOfTheLightTarget());
        this.addAbility(ability);

    }

    private KeeperOfTheLight(final KeeperOfTheLight card) {
        super(card);
    }

    @Override
    public KeeperOfTheLight copy() {
        return new KeeperOfTheLight(this);
    }
}

class KeeperOfTheLightTarget extends TargetPlayer {

    public KeeperOfTheLightTarget() {
        super(1, 1, false, new FilterOpponent("opponent that has more life than you"));
    }

    public KeeperOfTheLightTarget(final KeeperOfTheLightTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<UUID> possibleTargets = new HashSet<>();
        int lifeController = game.getPlayer(sourceControllerId).getLife();

        for (UUID targetId : availablePossibleTargets) {
            Player opponent = game.getPlayer(targetId);
            if (opponent != null) {
                int lifeOpponent = opponent.getLife();
                if (lifeOpponent > lifeController) {
                    possibleTargets.add(targetId);
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        int count = 0;
        MageObject targetSource = game.getObject(source);
        Player controller = game.getPlayer(sourceControllerId);
        if (controller != null && targetSource != null) {
            for (UUID playerId : game.getState().getPlayersInRange(sourceControllerId, game)) {
                Player player = game.getPlayer(playerId);
                if (player != null
                        && controller.getLife() < player.getLife()
                        && !player.hasLeft()
                        && filter.match(player, sourceControllerId, source, game)
                        && player.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    count++;
                    if (count >= this.minNumberOfTargets) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public KeeperOfTheLightTarget copy() {
        return new KeeperOfTheLightTarget(this);
    }
}
