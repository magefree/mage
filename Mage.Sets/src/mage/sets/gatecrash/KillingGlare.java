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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class KillingGlare extends CardImpl<KillingGlare> {

    public KillingGlare (UUID ownerId) {
        super(ownerId, 70, "Killing Glare", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{X}{B}");
        this.expansionSetCode = "GTC";

        this.color.setBlack(true);

        // Destroy target creature with power X or less.
        this.getSpellAbility().addEffect(new KillingGlareDestroyEffect());

    }

    public KillingGlare(final KillingGlare card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            Player controller = game.getPlayer(ability.getControllerId());
            if (controller.isHuman()) {
                ability.getTargets().clear();
                FilterCreaturePermanent filter = new FilterCreaturePermanent();
                filter.add(new PowerPredicate(Filter.ComparisonType.LessThan, ability.getManaCostsToPay().getX() + 1));
                ability.addTarget(new TargetCreaturePermanent(filter));
            }
        }
    }


    @Override
    public KillingGlare  copy() {
        return new KillingGlare(this);
    }
}

class KillingGlareDestroyEffect extends OneShotEffect<KillingGlareDestroyEffect> {

    public KillingGlareDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature with power X or less";
    }

    public KillingGlareDestroyEffect(final KillingGlareDestroyEffect effect) {
        super(effect);
    }

    @Override
    public KillingGlareDestroyEffect copy() {
        return new KillingGlareDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller.isHuman()) {
            Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (creature == null) {
                return false;
            }
            return creature.destroy(source.getSourceId(), game, false);
        } else {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new PowerPredicate(Filter.ComparisonType.LessThan, source.getManaCostsToPay().getX() + 1));
            Target target = new TargetCreaturePermanent(filter);
            if (controller.chooseTarget(outcome, target, source, game)) {
                Permanent creature = game.getPermanent(target.getFirstTarget());
                if (creature == null) {
                    return false;
                }
                return creature.destroy(source.getSourceId(), game, false);
            }
        }
        return false;
    }
}
