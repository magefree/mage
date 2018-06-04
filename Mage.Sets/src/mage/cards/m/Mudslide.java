
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class Mudslide extends CardImpl {

    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creatures without flying");

    static {
        filterCreature.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public Mudslide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Creatures without flying don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filterCreature)));

        // At the beginning of each player's upkeep, that player may choose any number of tapped creatures without flying he or she controls and pay {2} for each creature chosen this way. If the player does, untap those creatures.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new MudslideEffect(), TargetController.ANY, false));
    }

    public Mudslide(final Mudslide card) {
        super(card);
    }

    @Override
    public Mudslide copy() {
        return new Mudslide(this);
    }
}

class MudslideEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("tapped creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter.add(new TappedPredicate());
    }

    MudslideEffect() {
        super(Outcome.Benefit);
        staticText = "that player may choose any number of tapped creatures without flying he or she controls and pay {2} for each creature chosen this way. If the player does, untap those creatures.";
    }

    MudslideEffect(MudslideEffect effect) {
        super(effect);
    }

    @Override
    public MudslideEffect copy() {
        return new MudslideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            int countBattlefield = game.getBattlefield().getAllActivePermanents(filter, game.getActivePlayerId(), game).size();
            while (player.canRespond() && countBattlefield > 0 && player.chooseUse(Outcome.AIDontUseIt, "Pay {2} and untap a tapped creature without flying under your control?", source, game)) {
                Target tappedCreatureTarget = new TargetControlledCreaturePermanent(1, 1, filter, true);
                if (player.choose(Outcome.Detriment, tappedCreatureTarget, source.getSourceId(), game)) {
                    GenericManaCost cost = new GenericManaCost(2);
                    Permanent tappedCreature = game.getPermanent(tappedCreatureTarget.getFirstTarget());

                    if (cost.pay(source, game, source.getSourceId(), player.getId(), false)) {
                        tappedCreature.untap(game);
                    }
                }
                countBattlefield = game.getBattlefield().getAllActivePermanents(filter, game.getActivePlayerId(), game).size();
            }
            return true;
        }
        return false;
    }
}
