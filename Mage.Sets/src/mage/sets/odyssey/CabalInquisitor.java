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
package mage.sets.odyssey;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.EffectType;
import mage.constants.Rarity;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author cbt33
 */

public class CabalInquisitor extends CardImpl<CabalInquisitor> {

    public CabalInquisitor(UUID ownerId) {
        super(ownerId, 119, "Cabal Inquisitor", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Human");
        this.subtype.add("Minion");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Threshold - {1}{B}, {tap}, Exile two cards from your graveyard: Target player discards a card. Activate this ability only any time you could cast a sorcery, and only if seven or more cards are in your graveyard.
        Ability ability = new ActivateAsSorceryConditionalActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), new ManaCostsImpl("{1}{B}"), new CardsInControllerGraveCondition(7), "<br/><br/><i>Threshold</i> - {1}{B}, {tap}, Exile two cards from your graveyard: Target player discards a card. Activate this ability only any time you could cast a sorcery, and only if seven or more cards are in your graveyard.");
        ability.addTarget(new TargetPlayer(true));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, new FilterCard("cards from your graveyard"))));
        this.addAbility(ability);
    }

    public CabalInquisitor(final CabalInquisitor card) {
        super(card);
    }

    @Override
    public CabalInquisitor copy() {
        return new CabalInquisitor(this);
    }
}


class ActivateAsSorceryConditionalActivatedAbility extends ActivatedAbilityImpl<ActivateAsSorceryConditionalActivatedAbility> {
    
    private Condition condition;
    private String ruleText = "<i>Threshold<i/> - {1}{B}, {t}, Exile two cards from your graveyard: Target player discards a card. Activate this ability only any time you could cast a sorcery, and only if seven or more cards are in your graveyard.";

    private static final Effects emptyEffects = new Effects();

    public ActivateAsSorceryConditionalActivatedAbility(Zone zone, Effect effect, ManaCosts cost, Condition condition, String rule) {
        super(zone, effect, cost);
        this.condition = condition;
        this.ruleText = rule;
        timing = TimingRule.SORCERY; 
    }


    public ActivateAsSorceryConditionalActivatedAbility(final ActivateAsSorceryConditionalActivatedAbility ability) {
        super(ability);
        this.condition = ability.condition;
        this.ruleText = ability.ruleText;
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        if (!condition.apply(game, this)) {
            return emptyEffects;
        }
        return super.getEffects(game, effectType);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (!condition.apply(game, this)) {
            return false;
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public ActivateAsSorceryConditionalActivatedAbility copy() {
        return new ActivateAsSorceryConditionalActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText;
    }
}
