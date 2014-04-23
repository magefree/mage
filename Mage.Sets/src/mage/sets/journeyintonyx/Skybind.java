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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class Skybind extends CardImpl<Skybind> {

    private static final FilterPermanent filter = new FilterPermanent("nonenchantment permanent");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public Skybind(UUID ownerId) {
        super(ownerId, 25, "Skybind", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");
        this.expansionSetCode = "JOU";

        this.color.setWhite(true);

        // Constellation — When Skybind or another enchantment enters the battlefield under your control, exile target nonenchantment permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new ConstellationAbility(new SkybindEffect(), false);
        ability.addTarget(new TargetPermanent(filter, true));
        this.addAbility(ability);
    }

    public Skybind(final Skybind card) {
        super(card);
    }

    @Override
    public Skybind copy() {
        return new Skybind(this);
    }
}

class SkybindEffect extends OneShotEffect<SkybindEffect> {

    public SkybindEffect() {
        super(Outcome.Detriment);
        staticText = "exile target nonenchantment permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public SkybindEffect(final SkybindEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent != null && sourcePermanent != null) {
            if (permanent.moveToExile(source.getSourceId(), sourcePermanent.getName(), source.getId(), game)) {
                //create delayed triggered ability
                AtEndOfTurnDelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(new ReturnFromExileEffect(source.getSourceId(), Zone.BATTLEFIELD));
                delayedAbility.setSourceId(source.getSourceId());
                delayedAbility.setControllerId(source.getControllerId());
                game.addDelayedTriggeredAbility(delayedAbility);
                return true;
            }
        }
        return false;
    }

    @Override
    public SkybindEffect copy() {
        return new SkybindEffect(this);
    }
}
