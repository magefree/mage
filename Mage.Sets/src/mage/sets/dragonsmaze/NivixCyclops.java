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
package mage.sets.dragonsmaze;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class NivixCyclops extends CardImpl<NivixCyclops> {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public NivixCyclops(UUID ownerId) {
        super(ownerId, 87, "Nivix Cyclops", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Cyclops");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, Nivix Cyclops gets +3/+0 until end of turn and can attack this turn as though it didn't have defender.
        Ability ability = new SpellCastControllerTriggeredAbility(new BoostSourceEffect(3, 0, Duration.EndOfTurn), filter, false);
        ability.addEffect(new AsThoughNoDefenderEffect());
        this.addAbility(ability);

    }

    public NivixCyclops(final NivixCyclops card) {
        super(card);
    }

    @Override
    public NivixCyclops copy() {
        return new NivixCyclops(this);
    }    
}

class AsThoughNoDefenderEffect extends AsThoughEffectImpl<AsThoughNoDefenderEffect> {

    public AsThoughNoDefenderEffect() {
        super(AsThoughEffectType.ATTACK, Duration.EndOfTurn, Outcome.Benefit);
        staticText ="and it can attack as though it didn't have defender";
    }

    public AsThoughNoDefenderEffect(final AsThoughNoDefenderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AsThoughNoDefenderEffect copy() {
        return new AsThoughNoDefenderEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        Permanent nivixCyclops = game.getPermanent(source.getSourceId());
        if (nivixCyclops != null
                && nivixCyclops.getAbilities().containsKey(DefenderAbility.getInstance().getId())) {
            return true;
        }
        return false;
    }
}


