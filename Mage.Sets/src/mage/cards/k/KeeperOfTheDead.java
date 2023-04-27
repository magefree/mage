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
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author spjspj
 */
public final class KeeperOfTheDead extends CardImpl {

    private static final FilterPlayer filter = new FilterPlayer();

    static {
        filter.add(new KeeperOfDeadPredicate());
    }

    public KeeperOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {B}, {T}: Choose target opponent who had at least two fewer creature cards in their graveyard than you did as you activated this ability. Destroy target nonblack creature they control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KeeperOfTheDeadEffect(), new TapSourceCost());
        ability.addCost(new ManaCostsImpl<>("{B}"));
        ability.addTarget(new TargetPlayer(1, 1, false, filter));
        ability.addTarget(new KeeperOfTheDeadCreatureTarget());
        this.addAbility(ability);
    }

    private KeeperOfTheDead(final KeeperOfTheDead card) {
        super(card);
    }

    @Override
    public KeeperOfTheDead copy() {
        return new KeeperOfTheDead(this);
    }
}

class KeeperOfDeadPredicate implements ObjectSourcePlayerPredicate<Player> {

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        Permanent sourceObject = input.getSource().getSourcePermanentIfItStillExists(game);
        Player controller = null;
        if (sourceObject != null) {
            controller = game.getPlayer(sourceObject.getControllerId());
        }

        if (targetPlayer == null
                || controller == null
                || !controller.hasOpponent(targetPlayer.getId(), game)) {
            return false;
        }
        int countGraveyardTargetPlayer = targetPlayer.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURES, game).size();
        int countGraveyardController = controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURES, game).size();
        return countGraveyardController >= countGraveyardTargetPlayer + 2;
    }

    @Override
    public String toString() {
        return "opponent who had at least two fewer creature cards in their graveyard than you did as you activated this ability";
    }
}

class KeeperOfTheDeadCreatureTarget extends TargetPermanent {

    public KeeperOfTheDeadCreatureTarget() {
        super(1, 1, new FilterCreaturePermanent("nonblack creature that player controls"), false);
    }

    public KeeperOfTheDeadCreatureTarget(final KeeperOfTheDeadCreatureTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        UUID firstTarget = source.getFirstTarget();
        Permanent permanent = game.getPermanent(id);
        if (firstTarget != null && permanent != null && permanent.isControlledBy(firstTarget)) {
            return super.canTarget(id, source, game);
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject object = game.getObject(source);

        for (StackObject item : game.getState().getStack()) {
            if (item.getId().equals(source.getSourceId())) {
                object = item;
            }
            if (item.getSourceId().equals(source.getSourceId())) {
                object = item;
            }
        }

        if (object instanceof StackObject) {
            UUID playerId = ((StackObject) object).getStackAbility().getFirstTarget();
            for (UUID targetId : availablePossibleTargets) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null && StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK.match(permanent, game) && permanent.isControlledBy(playerId)) {
                    possibleTargets.add(targetId);
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public KeeperOfTheDeadCreatureTarget copy() {
        return new KeeperOfTheDeadCreatureTarget(this);
    }
}

class KeeperOfTheDeadEffect extends OneShotEffect {

    public KeeperOfTheDeadEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target nonblack creature contolled by target opponent who had at least two fewer creature cards in their graveyard than you did as you activated this ability";
    }

    public KeeperOfTheDeadEffect(final KeeperOfTheDeadEffect effect) {
        super(effect);
    }

    @Override
    public KeeperOfTheDeadEffect copy() {
        return new KeeperOfTheDeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getTargets().get(0).getFirstTarget());

        if (opponent != null) {
            Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (creature != null) {
                creature.destroy(source, game, false);
            }
        }
        return true;
    }
}
