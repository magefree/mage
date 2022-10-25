package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ArchfiendOfDepravity extends CardImpl {

    public ArchfiendOfDepravity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each opponent's end step, that player chooses up to two creatures they control, then sacrifices the rest.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ArchfiendOfDepravityEffect(), TargetController.OPPONENT, false
        ));
    }

    private ArchfiendOfDepravity(final ArchfiendOfDepravity card) {
        super(card);
    }

    @Override
    public ArchfiendOfDepravity copy() {
        return new ArchfiendOfDepravity(this);
    }
}

class ArchfiendOfDepravityEffect extends OneShotEffect {

    public ArchfiendOfDepravityEffect() {
        super(Outcome.Benefit); // AI should select two creatures if possible so it has to be a benefit
        this.staticText = "that player chooses up to two creatures they control, then sacrifices the rest";
    }

    public ArchfiendOfDepravityEffect(final ArchfiendOfDepravityEffect effect) {
        super(effect);
    }

    @Override
    public ArchfiendOfDepravityEffect copy() {
        return new ArchfiendOfDepravityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                0, 2, StaticFilters.FILTER_CONTROLLED_CREATURE, true
        );
        opponent.choose(outcome, target, source, game);
        List<Permanent> permanents = game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE, opponent.getId(), source, game);
        permanents.removeIf(permanent -> target.getTargets().contains(permanent.getId()));
        for (Permanent creature : permanents) {
            creature.sacrifice(source, game);
        }
        return true;
    }
}
