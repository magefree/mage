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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherCardPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class RisenExecutioner extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombie creatures");
    
    static {
        filter.add(new SubtypePredicate("Zombie"));
    }
    
    public RisenExecutioner(UUID ownerId) {
        super(ownerId, 116, "Risen Executioner", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Zombie");
        this.subtype.add("Warrior");
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Risen Executioner can't block.
        this.addAbility(new CantBlockAbility());
        
        // Other Zombie creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
                
        // You may cast Risen Executioner from your graveyard if you pay {1} more to cast it for each other creature card in your graveyard.
        // TODO: cost increase does not happen if Risen Executioner is cast grom graveyard because of other effects
        Ability ability = new SimpleStaticAbility(Zone.ALL, new RisenExecutionerCastEffect());
        ability.addEffect(new RisenExecutionerCostIncreasingEffect());
        this.addAbility(ability);
        
    }

    public RisenExecutioner(final RisenExecutioner card) {
        super(card);
    }

    @Override
    public RisenExecutioner copy() {
        return new RisenExecutioner(this);
    }
}

class RisenExecutionerCastEffect extends AsThoughEffectImpl {
    
    RisenExecutionerCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NON_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from your graveyard";
    }

    RisenExecutionerCastEffect(final RisenExecutionerCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public RisenExecutionerCastEffect copy() {
        return new RisenExecutionerCastEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null 
                    && card.getOwnerId().equals(affectedControllerId) 
                    && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }
}

class RisenExecutionerCostIncreasingEffect extends CostModificationEffectImpl {

    protected static final FilterCreatureCard filter = new FilterCreatureCard();
    
    static {
        filter.add(new AnotherCardPredicate());
    }
    
    RisenExecutionerCostIncreasingEffect () {
        super(Duration.EndOfGame, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "if you pay {1} more to cast it for each other creature card in your graveyard";
    }

    RisenExecutionerCostIncreasingEffect(final RisenExecutionerCostIncreasingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CardUtil.increaseCost(abilityToModify, controller.getGraveyard().count(filter, source.getSourceId(), source.getControllerId(), game));
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getSourceId().equals(source.getSourceId())) {
            Spell spell = game.getStack().getSpell(abilityToModify.getSourceId());
            return spell != null && spell.getFromZone() == Zone.GRAVEYARD;
        }
        return false;
    }

    @Override
    public RisenExecutionerCostIncreasingEffect copy() {
        return new RisenExecutionerCostIncreasingEffect(this);
    }

}
