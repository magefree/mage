package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CompletedDungeonCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WhitePlumeAdventurer extends CardImpl {

    public WhitePlumeAdventurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When White Plume Adventurer enters battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));

        // At the beginning of each opponent's upkeep, untap a creature you control. If you've completed a dungeon, untap all creatures you control instead.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new WhitePlumeAdventurerEffect(), TargetController.OPPONENT, false
        ).addHint(CompletedDungeonCondition.getHint()), new CompletedDungeonWatcher());
    }

    private WhitePlumeAdventurer(final WhitePlumeAdventurer card) {
        super(card);
    }

    @Override
    public WhitePlumeAdventurer copy() {
        return new WhitePlumeAdventurer(this);
    }
}

class WhitePlumeAdventurerEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("tapped creature you control");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    WhitePlumeAdventurerEffect() {
        super(Outcome.Benefit);
        staticText = "untap a creature you control. If you've completed a dungeon, " +
                "untap all creatures you control instead";
    }

    private WhitePlumeAdventurerEffect(final WhitePlumeAdventurerEffect effect) {
        super(effect);
    }

    @Override
    public WhitePlumeAdventurerEffect copy() {
        return new WhitePlumeAdventurerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.getBattlefield().contains(filter, source, game, 1)) {
            return false;
        }
        if (CompletedDungeonWatcher.checkPlayer(source.getControllerId(), game)) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(
                    StaticFilters.FILTER_CONTROLLED_CREATURE,
                    source.getControllerId(), source, game
            )) {
                permanent.untap(game);
            }
            return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.untap(game);
    }
}
