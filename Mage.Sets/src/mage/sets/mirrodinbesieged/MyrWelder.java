/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MyrWelder extends CardImpl<MyrWelder> {

    public MyrWelder(UUID ownerId) {
        super(ownerId, 118, "Myr Welder", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Myr");
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Imprint - {tap}: Exile target artifact card from a graveyard
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MyrWelderEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard(new FilterArtifactCard("artifact card from a graveyard")));
        this.addAbility(ability);

        // Myr Welder has all activated abilities of all cards exiled with it
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MyrWelderContinuousEffect()));

    }

    public MyrWelder(final MyrWelder card) {
        super(card);
    }

    @Override
    public MyrWelder copy() {
        return new MyrWelder(this);
    }

}

class MyrWelderEffect extends OneShotEffect<MyrWelderEffect> {

    public MyrWelderEffect() {
        super(Constants.Outcome.Exile);
        staticText = "Exile target artifact card from a graveyard";
    }

    public MyrWelderEffect(MyrWelderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (card != null && permanent != null) {
            card.moveToExile(getId(), "Myr Welder (Imprint)", source.getSourceId(), game);
            permanent.imprint(card.getId(), game);
            return true;
        }
        return false;
    }

    @Override
    public MyrWelderEffect copy() {
        return new MyrWelderEffect(this);
    }

}

class MyrWelderContinuousEffect extends ContinuousEffectImpl<MyrWelderContinuousEffect> {

    public MyrWelderContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all activated abilities of all cards exiled with it";
    }

    public MyrWelderContinuousEffect(final MyrWelderContinuousEffect effect) {
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
    public MyrWelderContinuousEffect copy() {
        return new MyrWelderContinuousEffect(this);
    }

}
