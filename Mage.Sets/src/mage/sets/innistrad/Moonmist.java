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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageEffect;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward
 */
public class Moonmist extends CardImpl<Moonmist> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures other than Werewolves and Wolves");

    static {
        filter.getSubtype().add("Werewolf");
        filter.getSubtype().add("Wolf");
        filter.setScopeSubtype(ComparisonScope.Any);
        filter.setNotFilter(true);
    }

    public Moonmist(UUID ownerId) {
        super(ownerId, 195, "Moonmist", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{G}");
        this.expansionSetCode = "ISD";

        this.color.setGreen(true);

        // Transform all Humans. Prevent all combat damage that would be dealt this turn by creatures other than Werewolves and Wolves.
        this.getSpellAbility().addEffect(new MoonmistEffect());
        this.getSpellAbility().addEffect(new PreventAllDamageEffect(filter, Duration.EndOfTurn, true));
    }

    public Moonmist(final Moonmist card) {
        super(card);
    }

    @Override
    public Moonmist copy() {
        return new Moonmist(this);
    }
}

class MoonmistEffect extends OneShotEffect<MoonmistEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("humans");

    static {
        filter.getSubtype().add("Human");
    }

    public MoonmistEffect() {
        super(Outcome.PreventDamage);
    }

    public MoonmistEffect(final MoonmistEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (permanent.canTransform()) {
                permanent.transform(game);
            }
        }
        return true;
    }

    @Override
    public MoonmistEffect copy() {
        return new MoonmistEffect(this);
    }

}