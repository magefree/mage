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
package mage.sets.apocalypse;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class OrimsThunder extends CardImpl<OrimsThunder> {

    private static final FilterPermanent filter = new FilterPermanent("artifact or enchantment");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public OrimsThunder(UUID ownerId) {
        super(ownerId, 15, "Orim's Thunder", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{W}");
        this.expansionSetCode = "APC";

        this.color.setWhite(true);

        // Kicker {R}
        this.addAbility(new KickerAbility("{R}"));

        // Destroy target artifact or enchantment. If Orim's Thunder was kicked, it deals damage equal to that permanent's converted mana cost to target creature.
        this.getSpellAbility().addEffect(new OrimsThunderEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new OrimsThunderEffect2(),
                KickedCondition.getInstance(),
                "If Orim's Thunder was kicked, it deals damage equal to that permanent's converted mana cost to target creature"));

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            if (KickedCondition.getInstance().apply(game, ability)) {
                ability.addTarget(new TargetCreaturePermanent());
            }
        }
    }

    public OrimsThunder(final OrimsThunder card) {
        super(card);
    }

    @Override
    public OrimsThunder copy() {
        return new OrimsThunder(this);
    }
}

class OrimsThunderEffect2 extends OneShotEffect<OrimsThunderEffect2> {

    OrimsThunderEffect2() {
        super(Outcome.Damage);
    }

    OrimsThunderEffect2(final OrimsThunderEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = 0;
        MageObject firstTarget = game.getLastKnownInformation(source.getFirstTarget(), Constants.Zone.BATTLEFIELD);
        Permanent secondTarget = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (firstTarget != null) {
            damage = firstTarget.getManaCost().convertedManaCost();
        }
        boolean kicked = KickedCondition.getInstance().apply(game, source);
        if (kicked && secondTarget != null) {
            secondTarget.damage(damage, source.getId(), game, true, false);
            return true;
        }
        return false;
    }

    @Override
    public OrimsThunderEffect2 copy() {
        return new OrimsThunderEffect2(this);
    }
}

class OrimsThunderEffect extends OneShotEffect<OrimsThunderEffect> {

    OrimsThunderEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target artifact or enchantment";
    }

    OrimsThunderEffect(final OrimsThunderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            return target.destroy(source.getId(), game, false);
        }
        return false;
    }

    @Override
    public OrimsThunderEffect copy() {
        return new OrimsThunderEffect(this);
    }
}