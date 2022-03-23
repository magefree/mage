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
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterOpponent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.BeastToken4;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author noahg
 */
public final class KeeperOfTheBeasts extends CardImpl {

    public KeeperOfTheBeasts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {G}, {tap}: Choose target opponent who controlled more creatures than you did as you activated this ability. Put a 2/2 green Beast creature token onto the battlefield.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new BeastToken4()).setText("Choose target opponent who controlled more creatures than you did as you activated this ability. Create a 2/2 green Beast creature token."),
                new ManaCostsImpl("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new KeeperOfTheBeastsTarget());
        this.addAbility(ability);
    }

    private KeeperOfTheBeasts(final KeeperOfTheBeasts card) {
        super(card);
    }

    @Override
    public KeeperOfTheBeasts copy() {
        return new KeeperOfTheBeasts(this);
    }
}

class KeeperOfTheBeastsTarget extends TargetPlayer {

    public KeeperOfTheBeastsTarget() {
        super(1, 1, false, new FilterOpponent("opponent that controls more creatures than you"));
    }

    public KeeperOfTheBeastsTarget(final KeeperOfTheBeastsTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<UUID> possibleTargets = new HashSet<>();
        int creaturesController = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, sourceControllerId, game);

        for (UUID targetId : availablePossibleTargets) {
            if (game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, targetId, game) > creaturesController) {
                possibleTargets.add(targetId);
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
                        && game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, sourceControllerId, game)
                        < game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game)
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
    public KeeperOfTheBeastsTarget copy() {
        return new KeeperOfTheBeastsTarget(this);
    }
}

