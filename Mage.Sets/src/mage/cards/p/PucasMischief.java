
package mage.cards.p;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class PucasMischief extends CardImpl {

    private static final String rule = "you may exchange control of target nonland permanent you control and target nonland permanent an opponent controls with an equal or lesser converted mana cost";

    public PucasMischief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}");

        // At the beginning of your upkeep, you may exchange control of target nonland permanent you control and target nonland permanent an opponent controls with an equal or lesser converted mana cost.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ExchangeControlTargetEffect(Duration.EndOfGame, rule, false, true), TargetController.YOU, true);
        ability.addTarget(new TargetControlledPermanentWithCMCGreaterOrLessThanOpponentPermanent());
        ability.addTarget(new PucasMischiefSecondTarget());
        this.addAbility(ability);

    }

    public PucasMischief(final PucasMischief card) {
        super(card);
    }

    @Override
    public PucasMischief copy() {
        return new PucasMischief(this);
    }
}

class TargetControlledPermanentWithCMCGreaterOrLessThanOpponentPermanent extends TargetControlledPermanent {

    public TargetControlledPermanentWithCMCGreaterOrLessThanOpponentPermanent() {
        super();
        this.filter = this.filter.copy();
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        setTargetName("nonland permanent you control");
    }

    public TargetControlledPermanentWithCMCGreaterOrLessThanOpponentPermanent(final TargetControlledPermanentWithCMCGreaterOrLessThanOpponentPermanent target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        return super.canTarget(controllerId, id, source, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(sourceId);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game)) {
            if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetControlledPermanentWithCMCGreaterOrLessThanOpponentPermanent copy() {
        return new TargetControlledPermanentWithCMCGreaterOrLessThanOpponentPermanent(this);
    }
}

class PucasMischiefSecondTarget extends TargetPermanent {

    private Permanent firstTarget = null;

    public PucasMischiefSecondTarget() {
        super();
        this.filter = this.filter.copy();
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        setTargetName("permanent an opponent controls with an equal or lesser converted mana cost");
    }

    public PucasMischiefSecondTarget(final PucasMischiefSecondTarget target) {
        super(target);
        this.firstTarget = target.firstTarget;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (super.canTarget(id, source, game)) {
            Permanent target1 = game.getPermanent(source.getFirstTarget());
            Permanent opponentPermanent = game.getPermanent(id);
            if (target1 != null && opponentPermanent != null) {
                return target1.getConvertedManaCost() >= opponentPermanent.getConvertedManaCost();
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        if (firstTarget != null) {
            MageObject targetSource = game.getObject(sourceId);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game)) {
                if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    if (firstTarget.getConvertedManaCost() >= permanent.getConvertedManaCost()) {
                        possibleTargets.add(permanent.getId());
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        firstTarget = game.getPermanent(source.getFirstTarget());
        return super.chooseTarget(Outcome.GainControl, playerId, source, game);
    }

    @Override
    public PucasMischiefSecondTarget copy() {
        return new PucasMischiefSecondTarget(this);
    }
}
