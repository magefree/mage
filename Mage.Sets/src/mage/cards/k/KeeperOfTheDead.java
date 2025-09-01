package mage.cards.k;

import mage.MageInt;
import mage.ObjectColor;
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
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.Set;
import java.util.UUID;

/**
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
        Ability ability = new SimpleActivatedAbility(new KeeperOfTheDeadEffect(), new TapSourceCost());
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

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature that player controls");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public KeeperOfTheDeadCreatureTarget() {
        super(1, 1, filter);
    }

    private KeeperOfTheDeadCreatureTarget(final KeeperOfTheDeadCreatureTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);

        Player needPlayer = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (needPlayer == null) {
            // playable or not selected - use any
        } else {
            // filter by controller
            possibleTargets.removeIf(id -> {
                Permanent permanent = game.getPermanent(id);
                return permanent == null
                        || permanent.getId().equals(source.getFirstTarget())
                        || !permanent.isControlledBy(needPlayer.getId());
            });
        }

        return possibleTargets;
    }

    @Override
    public KeeperOfTheDeadCreatureTarget copy() {
        return new KeeperOfTheDeadCreatureTarget(this);
    }
}

class KeeperOfTheDeadEffect extends OneShotEffect {

    KeeperOfTheDeadEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target nonblack creature contolled by target opponent who had at least two fewer creature cards in their graveyard than you did as you activated this ability";
    }

    private KeeperOfTheDeadEffect(final KeeperOfTheDeadEffect effect) {
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
