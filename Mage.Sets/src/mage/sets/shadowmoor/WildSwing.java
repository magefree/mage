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

import java.util.Random;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class WildSwing extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("target nonenchantment permanents");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public WildSwing(UUID ownerId) {
        super(ownerId, 108, "Wild Swing", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{R}");
        this.expansionSetCode = "SHM";

        // Choose three target nonenchantment permanents. Destroy one of them at random.
        this.getSpellAbility().addEffect(new WildSwingEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(3, filter));

    }

    public WildSwing(final WildSwing card) {
        super(card);
    }

    @Override
    public WildSwing copy() {
        return new WildSwing(this);
    }
}

class WildSwingEffect extends OneShotEffect {

    public WildSwingEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose three target nonenchantment permanents. Destroy one of them at random";
    }

    public WildSwingEffect(final WildSwingEffect effect) {
        super(effect);
    }

    @Override
    public WildSwingEffect copy() {
        return new WildSwingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (!source.getTargets().isEmpty() && sourceObject != null) {
            Target target = source.getTargets().get(0);
            if (target != null && !target.getTargets().isEmpty()) {
                Random rnd = new Random();
                Permanent targetPermanent = game.getPermanent(target.getTargets().get(rnd.nextInt(target.getTargets().size())));
                if (targetPermanent != null) {
                    game.informPlayers(sourceObject.getLogName() + ": The randomly chosen target to destroy is " + targetPermanent.getLogName());
                    targetPermanent.destroy(source.getSourceId(), game, false);
                }
                return true;
            }
        }
        return false;
    }
}
