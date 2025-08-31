package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterOpponent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.Set;
import java.util.UUID;

/**
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
        Ability ability = new SimpleActivatedAbility(
                new GainLifeEffect(3).setText("Choose target opponent who had more life than you did as you activated this ability. You gain 3 life."),
                new ManaCostsImpl<>("{W}"));
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

    private KeeperOfTheLightTarget(final KeeperOfTheLightTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);

        Player controller = game.getPlayer(sourceControllerId);
        if (controller == null) {
            return possibleTargets;
        }

        possibleTargets.removeIf(playerId -> {
            Player player = game.getPlayer(playerId);
            return player == null || player.getLife() >= controller.getLife();
        });

        return possibleTargets;
    }

    @Override
    public KeeperOfTheLightTarget copy() {
        return new KeeperOfTheLightTarget(this);
    }
}
