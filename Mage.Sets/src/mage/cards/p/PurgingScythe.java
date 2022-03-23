
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class PurgingScythe extends CardImpl {

    public PurgingScythe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // At the beginning of your upkeep, Purging Scythe deals 2 damage to the creature with the least toughness. If two or more creatures are tied for least toughness, you choose one of them.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new PurgingScytheEffect(), TargetController.YOU, false));
    }

    private PurgingScythe(final PurgingScythe card) {
        super(card);
    }

    @Override
    public PurgingScythe copy() {
        return new PurgingScythe(this);
    }
}

class PurgingScytheEffect extends OneShotEffect {

    public PurgingScytheEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "{this} deals 2 damage to the creature with the least toughness. "
                + "If two or more creatures are tied for least toughness, you choose one of them";
    }

    public PurgingScytheEffect(final PurgingScytheEffect effect) {
        super(effect);
    }

    @Override
    public PurgingScytheEffect copy() {
        return new PurgingScytheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            int leastToughness = Integer.MAX_VALUE;
            boolean multipleExist = false;
            Permanent permanentToDamage = null;
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, source.getControllerId(), game)) {
                if (permanent.getToughness().getValue() < leastToughness) {
                    permanentToDamage = permanent;
                    leastToughness = permanent.getToughness().getValue();
                    multipleExist = false;
                } else {
                    if (permanent.getToughness().getValue() == leastToughness) {
                        multipleExist = true;
                    }
                }
            }
            if (multipleExist) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("one of the creatures with the least toughness");
                filter.add(new ToughnessPredicate(ComparisonType.EQUAL_TO, leastToughness));
                Target target = new TargetPermanent(filter);
                target.setNotTarget(true);
                if (target.canChoose(source.getControllerId(), source, game)) {
                    if (controller.choose(outcome, target, source, game)) {
                        permanentToDamage = game.getPermanent(target.getFirstTarget());
                    }
                }
            }
            if (permanentToDamage != null) {
                game.informPlayers(sourcePermanent.getName() + " chosen creature: " + permanentToDamage.getName());
                return permanentToDamage.damage(2, source.getSourceId(), source, game, false, true) > 0;
            }
            return true;
        }

        return false;
    }
}
