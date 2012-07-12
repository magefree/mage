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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author North
 */
public class SphinxBoneWand extends CardImpl<SphinxBoneWand> {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public SphinxBoneWand(UUID ownerId) {
        super(ownerId, 225, "Sphinx-Bone Wand", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{7}");
        this.expansionSetCode = "ROE";

        // Whenever you cast an instant or sorcery spell, you may put a charge counter on Sphinx-Bone Wand. If you do, Sphinx-Bone Wand deals damage equal to the number of charge counters on it to target creature or player.
        SpellCastTriggeredAbility ability = new SpellCastTriggeredAbility(new SphinxBoneWandEffect(), filter, true);
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public SphinxBoneWand(final SphinxBoneWand card) {
        super(card);
    }

    @Override
    public SphinxBoneWand copy() {
        return new SphinxBoneWand(this);
    }
}

class SphinxBoneWandEffect extends OneShotEffect<SphinxBoneWandEffect> {

    public SphinxBoneWandEffect() {
        super(Outcome.Damage);
        this.staticText = "put a charge counter on Sphinx-Bone Wand. If you do, Sphinx-Bone Wand deals damage equal to the number of charge counters on it to target creature or player";
    }

    public SphinxBoneWandEffect(final SphinxBoneWandEffect effect) {
        super(effect);
    }

    @Override
    public SphinxBoneWandEffect copy() {
        return new SphinxBoneWandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            sourcePermanent.addCounters(CounterType.CHARGE.createInstance(), game);
            int amount = sourcePermanent.getCounters().getCount(CounterType.CHARGE);

            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                permanent.damage(amount, source.getSourceId(), game, true, false);
            }
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) {
                player.damage(amount, source.getSourceId(), game, false, true);
            }

            return true;
        }
        return false;
    }
}
