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
package mage.sets.invasion;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author LevelX2
 */
public class VerdelothTheAncient extends CardImpl<VerdelothTheAncient> {

    public VerdelothTheAncient(UUID ownerId) {
        super(ownerId, 220, "Verdeloth the Ancient", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.expansionSetCode = "INV";
        this.supertype.add("Legendary");
        this.subtype.add("Treefolk");

        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Kicker {X}
        this.addAbility(new KickerAbility("{X}"));
        
        // Saproling creatures and other Treefolk creatures get +1/+1.
        FilterCreaturePermanent filter = new FilterCreaturePermanent("Saproling creatures and other Treefolk creatures");
        filter.add(Predicates.or(
                Predicates.and(new SubtypePredicate("Treefolk"), Predicates.not(new PermanentIdPredicate(this.getId()))),
                new SubtypePredicate("Saproling"))
                );
        filter.add(Predicates.not(new PermanentIdPredicate(this.getId())));
                
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1,1, Duration.WhileOnBattlefield, filter, false)));
        
        // When Verdeloth the Ancient enters the battlefield, if it was kicked, put X 1/1 green Saproling creature tokens onto the battlefield.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SaprolingToken(), new GetKickerXValue()), false);
        this.addAbility(new ConditionalTriggeredAbility(ability, KickedCondition.getInstance(), 
                "When {this} enters the battlefield, if it was kicked, put X 1/1 green Saproling creature tokens onto the battlefield."));
        
    }

    public VerdelothTheAncient(final VerdelothTheAncient card) {
        super(card);
    }

    @Override
    public VerdelothTheAncient copy() {
        return new VerdelothTheAncient(this);
    }
}

class GetKickerXValue implements DynamicValue {

    public GetKickerXValue() {
    }

    @Override
    public int calculate(Game game, Ability source) {
        int count = 0;
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            for (Ability ability: card.getAbilities()) {
                if (ability instanceof KickerAbility) {
                    count += ((KickerAbility) ability).getXManaValue();
                }
            }
        }
        return count;
    }

    @Override
    public GetKickerXValue copy() {
        return new GetKickerXValue();
    }

    @Override
    public String toString() {
        return "X";
    };

    @Override
    public String getMessage() {
        return "X";
    };
}