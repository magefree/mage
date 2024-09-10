
package mage.cards.s;

import java.util.Objects;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class SpreadingPlague extends CardImpl {

    public SpreadingPlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");

        // Whenever a creature enters the battlefield, destroy all other creatures that share a color with it. They can't be regenerated.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new SpreadingPlagueEffect(), StaticFilters.FILTER_PERMANENT_CREATURE, false, SetTargetPointer.PERMANENT));

    }

    private SpreadingPlague(final SpreadingPlague card) {
        super(card);
    }

    @Override
    public SpreadingPlague copy() {
        return new SpreadingPlague(this);
    }
}

class SpreadingPlagueEffect extends OneShotEffect {

    static final FilterPermanent FILTER = new FilterPermanent("creature");

    static {
        FILTER.add(CardType.CREATURE.getPredicate());
    }

    SpreadingPlagueEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy all other creatures that share a color with it. They can't be regenerated";
    }

    private SpreadingPlagueEffect(final SpreadingPlagueEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (creature != null) {
            ObjectColor color = creature.getColor(game);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(FILTER, source.getControllerId(), game)) {
                if (permanent.getColor(game).shares(color)
                        && !Objects.equals(permanent, creature)) {
                    permanent.destroy(source, game, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SpreadingPlagueEffect copy() {
        return new SpreadingPlagueEffect(this);
    }
}
