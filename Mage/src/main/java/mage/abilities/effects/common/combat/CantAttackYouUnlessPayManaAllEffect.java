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
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class CantAttackYouUnlessPayManaAllEffect extends PayCostToAttackBlockEffectImpl {

    private final FilterCreaturePermanent filterCreaturePermanent;
    private final boolean payAlsoForAttackingPlaneswalker;

    public CantAttackYouUnlessPayManaAllEffect(ManaCosts manaCosts) {
        this(manaCosts, false);
    }

    public CantAttackYouUnlessPayManaAllEffect(ManaCosts manaCosts, boolean payAlsoForAttackingPlaneswalker) {
        this(manaCosts, payAlsoForAttackingPlaneswalker, null);
    }

    public CantAttackYouUnlessPayManaAllEffect(ManaCosts manaCosts, boolean payAlsoForAttackingPlaneswalker, FilterCreaturePermanent filter) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK, manaCosts);
        this.payAlsoForAttackingPlaneswalker = payAlsoForAttackingPlaneswalker;
        this.filterCreaturePermanent = filter;
        staticText = (filterCreaturePermanent == null ? "Creatures" : filterCreaturePermanent.getMessage())
                + " can't attack you "
                + (payAlsoForAttackingPlaneswalker ? "or a planeswalker you control " : "")
                + "unless their controller pays "
                + (manaCosts == null ? "" : manaCosts.getText())
                + " for each creature he or she controls that's attacking you";
    }

    public CantAttackYouUnlessPayManaAllEffect(final CantAttackYouUnlessPayManaAllEffect effect) {
        super(effect);
        this.payAlsoForAttackingPlaneswalker = effect.payAlsoForAttackingPlaneswalker;
        this.filterCreaturePermanent = effect.filterCreaturePermanent;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // check if attacking creature fullfills filter criteria
        if (filterCreaturePermanent != null) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (!filterCreaturePermanent.match(permanent, source.getSourceId(), source.getControllerId(), game)) {
                return false;
            }
        }
        // attack target is controlling player
        if (source.getControllerId().equals(event.getTargetId())) {
            return true;
        }
        // or attack target is a planeswalker of the controlling player
        if (payAlsoForAttackingPlaneswalker) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null
                    && permanent.isPlaneswalker()
                    && permanent.getControllerId().equals(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CantAttackYouUnlessPayManaAllEffect copy() {
        return new CantAttackYouUnlessPayManaAllEffect(this);
    }
}
