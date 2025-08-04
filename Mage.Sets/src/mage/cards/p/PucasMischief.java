
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class PucasMischief extends CardImpl {

    private static final String rule = "you may exchange control of target nonland permanent you control and target nonland permanent an opponent controls with an equal or lesser mana value";

    public PucasMischief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // At the beginning of your upkeep, you may exchange control of target nonland permanent you control and target nonland permanent an opponent controls with an equal or lesser converted mana cost.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ExchangeControlTargetEffect(Duration.EndOfGame, rule, false, true), true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND));
        ability.addTarget(new PucasMischiefSecondTarget());
        this.addAbility(ability);

    }

    private PucasMischief(final PucasMischief card) {
        super(card);
    }

    @Override
    public PucasMischief copy() {
        return new PucasMischief(this);
    }
}

class PucasMischiefSecondTarget extends TargetPermanent {

    public PucasMischiefSecondTarget() {
        super(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND);
        withTargetName("permanent an opponent controls with an equal or lesser mana value");
    }

    private PucasMischiefSecondTarget(final PucasMischiefSecondTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Permanent ownPermanent = game.getPermanent(source.getFirstTarget());
        Permanent possiblePermanent = game.getPermanent(id);
        if (ownPermanent == null || possiblePermanent == null) {
            return false;
        }
        return super.canTarget(id, source, game) && ownPermanent.getManaValue() >= possiblePermanent.getManaValue();
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();

        Permanent ownPermanent = game.getPermanent(source.getFirstTarget());
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
            if (ownPermanent == null) {
                // playable or first target not yet selected
                // use all
                possibleTargets.add(permanent.getId());
            } else {
                // real
                // filter by cmc
                if (ownPermanent.getManaValue() >= permanent.getManaValue()) {
                    possibleTargets.add(permanent.getId());
                }
            }
        }
        possibleTargets.removeIf(id -> ownPermanent != null && ownPermanent.getId().equals(id));

        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        // AI hint with better outcome
        return super.chooseTarget(Outcome.GainControl, playerId, source, game);
    }

    @Override
    public PucasMischiefSecondTarget copy() {
        return new PucasMischiefSecondTarget(this);
    }
}
