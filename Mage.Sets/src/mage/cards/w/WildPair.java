
package mage.cards.w;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.IntComparePredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author fenhl
 */
public final class WildPair extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature");

    static {
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public WildPair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{G}");

        // Whenever a creature enters the battlefield, if you cast it from your hand, you may search your library for a creature card with the same total power and toughness and put it onto the battlefield. If you do, shuffle your library.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new WildPairEffect(), filter, true, SetTargetPointer.PERMANENT, ""),
                new CastFromHandTargetCondition(),
                "Whenever a creature enters the battlefield, if you cast it from your hand, you may search your library for a creature card with the same total power and toughness and put it onto the battlefield. If you do, shuffle your library."
        ), new CastFromHandWatcher());
    }

    public WildPair(final WildPair card) {
        super(card);
    }

    @Override
    public WildPair copy() {
        return new WildPair(this);
    }
}

class WildPairEffect extends OneShotEffect {

    public WildPairEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "search your library for a creature card with the same total power and toughness and put it onto the battlefield";
    }

    public WildPairEffect(final WildPairEffect effect) {
        super(effect);
    }

    @Override
    public WildPairEffect copy() {
        return new WildPairEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                int totalPT = permanent.getPower().getValue() + permanent.getToughness().getValue();
                FilterCreatureCard filter = new FilterCreatureCard("creature card with total power and toughness " + totalPT);
                filter.add(new TotalPowerAndToughnessPredicate(ComparisonType.EQUAL_TO, totalPT));
                TargetCardInLibrary target = new TargetCardInLibrary(1, filter);
                if (controller.searchLibrary(target, source, game)) {
                    if (!target.getTargets().isEmpty()) {
                        controller.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
                    }
                }
                controller.shuffleLibrary(source, game);
                return true;
            }
        }
        return false;
    }
}

/**
 *
 * @author fenhl
 */
class TotalPowerAndToughnessPredicate extends IntComparePredicate<MageObject> {

    public TotalPowerAndToughnessPredicate(ComparisonType type, int value) {
        super(type, value);
    }

    @Override
    protected int getInputValue(MageObject input) {
        return input.getPower().getValue() + input.getToughness().getValue();
    }

    @Override
    public String toString() {
        return "TotalPowerAndToughness" + super.toString();
    }
}

class CastFromHandTargetCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getEffects().get(0).getTargetPointer().getFirst(game, source);
        Permanent permanent = game.getPermanentEntering(targetId);
        int zccDiff = 0;
        if (permanent == null) {
            permanent = game.getPermanentOrLKIBattlefield(targetId); // can be alredy again removed from battlefield so also check LKI
            zccDiff = -1;
        }
        if (permanent != null) {
            // check that the spell is still in the LKI
            Spell spell = game.getStack().getSpell(targetId);
            if (spell == null || spell.getZoneChangeCounter(game) != permanent.getZoneChangeCounter(game) + zccDiff) {
                if (game.getLastKnownInformation(targetId, Zone.STACK, permanent.getZoneChangeCounter(game) + zccDiff) == null) {
                    return false;
                }
            }
            CastFromHandWatcher watcher = game.getState().getWatcher(CastFromHandWatcher.class);
            if (watcher != null && watcher.spellWasCastFromHand(targetId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "you cast it from your hand";
    }

}
