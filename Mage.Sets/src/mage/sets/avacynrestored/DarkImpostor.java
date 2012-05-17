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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ImprintTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author noxx

 */
public class DarkImpostor extends CardImpl<DarkImpostor> {

    public DarkImpostor(UUID ownerId) {
        super(ownerId, 92, "Dark Impostor", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Vampire");
        this.subtype.add("Assassin");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{B}{B}: Exile target creature and put a +1/+1 counter on Dark Impostor.\
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ImprintTargetEffect(), new ManaCostsImpl("{4}{B}{B}"));
        ability.addEffect(new ExileTargetEffect(null, "Dark Impostor"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Dark Impostor has all activated abilities of all creature cards exiled with it.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new DarkImpostorContinuousEffect()));
    }

    public DarkImpostor(final DarkImpostor card) {
        super(card);
    }

    @Override
    public DarkImpostor copy() {
        return new DarkImpostor(this);
    }
}

class DarkImpostorContinuousEffect extends ContinuousEffectImpl<DarkImpostorContinuousEffect> {

    public DarkImpostorContinuousEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Layer.AbilityAddingRemovingEffects_6, Constants.SubLayer.NA, Constants.Outcome.AddAbility);
        staticText = "{this} has all activated abilities of all creature cards exiled with it";
    }

    public DarkImpostorContinuousEffect(final DarkImpostorContinuousEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm != null) {
            for (UUID imprintedId: perm.getImprinted()) {
                Card card = game.getCard(imprintedId);
                if (card != null) {
                    for (Ability ability: card.getAbilities()) {
                        if (ability instanceof ActivatedAbility) {
                            perm.addAbility(ability, game);
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public DarkImpostorContinuousEffect copy() {
        return new DarkImpostorContinuousEffect(this);
    }

}