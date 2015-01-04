/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.keyword;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.TurnFaceUpAbility;
import mage.abilities.costs.AlternativeCost2Impl;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continious.SourceEffect;
import mage.cards.Card;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * 702.36. Morph
 *
 *  702.36a Morph is a static ability that functions in any zone from which you could play
 *  the card its on, and the morph effect works any time the card is face down.
 *  "Morph [cost]" means "You may cast this card as a 2/2 face-down creature, with no text,
 *  no name, no subtypes, and no mana cost by paying {3} rather than paying its mana cost."
 * (See rule 707, "Face-Down Spells and Permanents.")
 *
 *  702.36b To cast a card using its morph ability, turn it face down. It becomes a 2/2
 *  face-down creature card, with no text, no name, no subtypes, and no mana cost. Any
 *  effects or prohibitions that would apply to casting a card with these characteristics
 *  (and not the face-up cards characteristics) are applied to casting this card. These
 *  values are the copiable values of that objects characteristics. (See rule 613,
 *  "Interaction of Continuous Effects," and rule 706, "Copying Objects.") Put it onto the
 *  stack (as a face-down spell with the same characteristics), and pay {3} rather than pay
 *  its mana cost. This follows the rules for paying alternative costs. You can use morph
 *  to cast a card from any zone from which you could normally play it. When the spell
 *  resolves, it enters the battlefield with the same characteristics the spell had. The
 *  morph effect applies to the face-down object wherever it is, and it ends when the
 *  permanent is turned face up. #
 *
 *  702.36c You cant cast a card face down if it doesnt have morph.
 *
 *  702.36d If you have priority, you may turn a face-down permanent you control face up.
 *  This is a special action; it doesnt use the stack (see rule 115). To do this, show
 *  all players what the permanents morph cost would be if it were face up, pay that cost,
 *  then turn the permanent face up. (If the permanent wouldnt have a morph cost if it
 *  were face up, it cant be turned face up this way.) The morph effect on it ends, and
 *  it regains its normal characteristics. Any abilities relating to the permanent entering
 *  the battlefield dont trigger when its turned face up and dont have any effect, because
 *  the permanent has already entered the battlefield.
 *
 *  702.36e See rule 707, "Face-Down Spells and Permanents," for more information on how to
 *  cast cards with morph.
 *
 * @author LevelX2
 */

public class MorphAbility extends StaticAbility implements AlternativeSourceCosts {

    protected static final String ABILITY_KEYWORD = "Morph";
    protected static final String REMINDER_TEXT = "<i>(You may cast this card face down as a 2/2 creature for {3}. Turn it face up any time for its morph cost.)</i>";
    protected String ruleText;
    protected AlternativeCost2Impl alternateCosts = new AlternativeCost2Impl(ABILITY_KEYWORD, REMINDER_TEXT, new GenericManaCost(3));
    protected Costs<Cost> morphCosts;
    // needed to check activation status, if card changes zone after casting it
    private   int zoneChangeCounter = 0;

    public MorphAbility(Card card, Cost morphCost) {
        this(card, createCosts(morphCost));
    }

    public MorphAbility(Card card, Costs<Cost> morphCosts) {
        super(Zone.HAND, null);
        this.morphCosts = morphCosts;
        this.setWorksFaceDown(true);
        name = ABILITY_KEYWORD;
        StringBuilder sb = new StringBuilder();
        sb.append(ABILITY_KEYWORD).append(" ");
        for (Cost cost :morphCosts) {
            if (!(cost instanceof ManaCosts)) {
                sb.append("- ");
                break;
            }
        }
        sb.append(morphCosts.getText()).append(" ");
        sb.append(REMINDER_TEXT);
        ruleText = sb.toString();

        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesFaceDownCreatureEffect(morphCosts));
        ability.setRuleVisible(false);
        ability.setWorksFaceDown(true);
        addSubAbility(ability);

    }

    public MorphAbility(final MorphAbility ability) {
       super(ability);
       this.zoneChangeCounter = ability.zoneChangeCounter;
       this.ruleText = ability.ruleText;
       this.alternateCosts = ability.alternateCosts.copy();
       this.morphCosts = ability.morphCosts; // can't be changed
    }

    private static Costs<Cost> createCosts(Cost cost) {
        Costs<Cost> costs = new CostsImpl<>();
        costs.add(cost);
        return costs;
    }

    @Override
    public MorphAbility copy() {
       return new MorphAbility(this);
    }

    public void resetMorph() {
        alternateCosts.reset();        
        zoneChangeCounter = 0;
    }

    public Costs<Cost> getMorphCosts() {
        return morphCosts;
    }

    @Override
    public boolean isActivated(Ability ability, Game game) {
        if (game.getZoneChangeCounter(sourceId) <= zoneChangeCounter +1) {
            return alternateCosts.isActivated(game);
        }
        return false;
    }

    @Override
    public boolean isAvailable(Ability source, Game game) {
        return true;
    }

    @Override
    public boolean askToActivateAlternativeCosts(Ability ability, Game game) {
        if (ability.getAbilityType().equals(AbilityType.SPELL)) {
            Player player = game.getPlayer(controllerId);
            Spell spell = game.getStack().getSpell(ability.getId());
            if (player != null && spell != null) {
                this.resetMorph();
                spell.setFaceDown(true, game); // so only the back is visible
                if (alternateCosts.canPay(ability, sourceId, controllerId, game)) {
                    if (player.chooseUse(Outcome.Benefit, new StringBuilder("Cast this card as a 2/2 face-down creature for ").append(getCosts().getText()).append(" ?").toString(), game)) {
                        activateMorph(game);
                        // change mana costs
                        ability.getManaCostsToPay().clear();
                        ability.getCosts().clear();
                        for (Iterator it = this.alternateCosts.iterator(); it.hasNext();) {
                            Cost cost = (Cost) it.next();
                            if (cost instanceof ManaCost) {
                                ability.getManaCostsToPay().add((ManaCost)cost.copy());
                            } else {
                                ability.getCosts().add(cost.copy());
                            }
                        }
                        // change spell colors
                        ObjectColor spellColor = spell.getColor();
                        spellColor.setBlack(false);
                        spellColor.setRed(false);
                        spellColor.setGreen(false);
                        spellColor.setWhite(false);
                        spellColor.setBlue(false);
                    } else {
                        spell.setFaceDown(false, game);
                    }
                }
            }
        }
       if (ability.getAbilityType().equals(AbilityType.PLAY_LAND)) {
            Player player = game.getPlayer(controllerId);
            if (player != null) {
                this.resetMorph();
                if (alternateCosts.canPay(ability, sourceId, controllerId, game)) {
                    if (player.chooseUse(Outcome.Benefit, new StringBuilder("Cast this card as a 2/2 face-down creature for ").append(getCosts().getText()).append(" ?").toString(), game)) {
                        activateMorph(game);
                        // change mana costs
                        ability.getManaCostsToPay().clear();
                        ability.getCosts().clear();
                        for (Iterator it = this.alternateCosts.iterator(); it.hasNext();) {
                            Cost cost = (Cost) it.next();
                            if (cost instanceof ManaCost) {
                                ability.getManaCostsToPay().add((ManaCost)cost.copy());
                            } else {
                                ability.getCosts().add(cost.copy());
                            }
                        }
                    }
                }
            }
        }
        return isActivated(ability, game);
    }

    private void activateMorph(Game game) {
        alternateCosts.activate();
        // remember zone change counter
        if (zoneChangeCounter == 0) {
            zoneChangeCounter = game.getZoneChangeCounter(getSourceId());
        }
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return ruleText;
    }

    @Override
    public String getCastMessageSuffix(Game game) {
        StringBuilder sb = new StringBuilder();
        int position = 0;
        sb.append(alternateCosts.getCastSuffixMessage(position));
        return sb.toString();
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public Costs<Cost> getCosts() {
        return alternateCosts;
    }
    
    public static void setPermanentToMorph(Permanent permanent, Game game) {
        permanent.getPower().initValue(2);
        permanent.getToughness().initValue(2);
        permanent.clearAbilities(game);
        permanent.getColor().setColor(new ObjectColor());
        permanent.setName("");
        permanent.getCardType().clear();
        permanent.getCardType().add(CardType.CREATURE);
        permanent.getSubtype().clear();
        permanent.getSupertype().clear();
        permanent.getManaCost().clear();
        permanent.setExpansionSetCode("KTK");
        permanent.setRarity(Rarity.NA);
        
    }
}

/**
 * This effect lets the creature always be a 2/2 face-down creature, with no text,
 * no name, no subtypes, and no mana cost, if it's face down on the battlefield.
 * And it adds the MorphTurnFaceUpAbility ability.
 * TODO: Check if it's better to create this effect always as a creature on the battelfield turns face down or
 * a creature enters the battlefield face down. Then the effect could be removed as the permanent turns face up.
 *
 * @author LevelX2
 */

class BecomesFaceDownCreatureEffect extends ContinuousEffectImpl implements SourceEffect {

    protected int zoneChangeCounter;
    protected Ability turnFaceUpAbility = null;

    public BecomesFaceDownCreatureEffect(Costs<Cost> morphCosts) {
        super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
        this.zoneChangeCounter = Integer.MIN_VALUE;
        if (morphCosts != null) {
            this.turnFaceUpAbility = new TurnFaceUpAbility(morphCosts);
        }
        staticText = "{this} becomes a 2/2 face-down creature, with no text, no name, no subtypes, and no mana cost";
    }

    public BecomesFaceDownCreatureEffect(final BecomesFaceDownCreatureEffect effect) {
        super(effect);
        this.zoneChangeCounter = effect.zoneChangeCounter;
        this.turnFaceUpAbility = effect.turnFaceUpAbility.copy();
    }

    @Override
    public BecomesFaceDownCreatureEffect copy() {
        return new BecomesFaceDownCreatureEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.isFaceDown(game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.setName("");
                    permanent.getSupertype().clear();
                    permanent.getCardType().clear();
                    permanent.getCardType().add(CardType.CREATURE);
                    permanent.getSubtype().clear();
                    permanent.getManaCost().clear();
                    break;
                case ColorChangingEffects_5:
                    permanent.getColor().setColor(new ObjectColor());
                    break;
                case AbilityAddingRemovingEffects_6:
                    Card card = game.getCard(permanent.getId()); //  
                    List<Ability> abilities = new ArrayList<>();
                    for (Ability ability : permanent.getAbilities(game)) {
                        if (card != null && !card.getAbilities(game).contains(ability)) {
                            // gained abilities from other sources won't be removed
                            continue;
                        }
                        if (ability.getWorksFaceDown()) {
                            ability.setRuleVisible(false);
                            continue;
                        } else {
                            if (!ability.getRuleVisible() && !ability.getEffects().isEmpty()) {
                                if (ability.getEffects().get(0) instanceof BecomesFaceDownCreatureEffect) {
                                    continue;
                                }
                            }
                        }
                        abilities.add(ability);
                    }
                    permanent.getAbilities(game).removeAll(abilities);
                    if (turnFaceUpAbility != null) {
                        permanent.addAbility(turnFaceUpAbility, game);
                    }
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setValue(2);
                        permanent.getToughness().setValue(2);
                    }

            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

}

