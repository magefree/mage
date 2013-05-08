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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.ScavengeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class VarolzTheScarStriped extends CardImpl<VarolzTheScarStriped> {
    
    private final static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature");
    
    static {
        filter.add(new AnotherPredicate());
    }

    public VarolzTheScarStriped(UUID ownerId) {
        super(ownerId, 112, "Varolz, the Scar-Striped", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        this.expansionSetCode = "DGM";
        this.supertype.add("Legendary");
        this.subtype.add("Troll");
        this.subtype.add("Warrior");

        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each creature card in your graveyard has scavenge. The scavenge cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VarolzTheScarStripedEffect()));

        // Sacrifice another creature: Regenerate Varolz, the Scar-Striped.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true))));
    }

    public VarolzTheScarStriped(final VarolzTheScarStriped card) {
        super(card);
    }

    @Override
    public VarolzTheScarStriped copy() {
        return new VarolzTheScarStriped(this);
    }
}

class VarolzTheScarStripedEffect extends ContinuousEffectImpl<VarolzTheScarStripedEffect> {

    VarolzTheScarStripedEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Layer.AbilityAddingRemovingEffects_6, Constants.SubLayer.NA, Constants.Outcome.AddAbility);
        staticText = "Each creature card in your graveyard has scavenge. The scavenge cost is equal to its mana cost";
    }

    VarolzTheScarStripedEffect(final VarolzTheScarStripedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID cardId : controller.getGraveyard()) {
                Card card = game.getCard(cardId);
                if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                    ScavengeAbility ability = new ScavengeAbility(new ManaCostsImpl(card.getManaCost().getText()));
                    ability.setSourceId(cardId);
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(cardId, ability);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public VarolzTheScarStripedEffect copy() {
        return new VarolzTheScarStripedEffect(this);
    }
}
