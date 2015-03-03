/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.shadowmoor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
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
public class PucasMischief extends CardImpl {

    private static final String rule = "you may exchange control of target nonland permanent you control and target nonland permanent an opponent controls with an equal or lesser converted mana cost";

    public PucasMischief(UUID ownerId) {
        super(ownerId, 47, "Puca's Mischief", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");
        this.expansionSetCode = "SHM";

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
                return target1.getManaCost().convertedManaCost() >= opponentPermanent.getManaCost().convertedManaCost();
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
                    if (firstTarget.getManaCost().convertedManaCost() >= permanent.getManaCost().convertedManaCost()) {
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
