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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesChosenCreatureTypeSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubTypeSet;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public class MistformWarchief extends CardImpl {

    private static final FilterCard filter = new FilterCard("Creature spells you cast that share a creature type with {this}");

    static {
        filter.add(new MistformWarchiefPredicate());
    }

    public MistformWarchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Creature spells you cast that share a creature type with Mistform Warchief cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new SpellsCostReductionControllerEffect(filter, 1)
                        .setText("Creature spells you cast that share a creature type with {this} cost {1} less to cast")
        ));

        // {tap}: Mistform Warchief becomes the creature type of your choice until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesChosenCreatureTypeSourceEffect(), new TapSourceCost()));
    }

    public MistformWarchief(final MistformWarchief card) {
        super(card);
    }

    @Override
    public MistformWarchief copy() {
        return new MistformWarchief(this);
    }
}

class MistformWarchiefPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<MageObject>> {

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        MageObject sourceObject = game.getObject(input.getSourceId());
        if (sourceObject != null) {
            for (SubType subType : sourceObject.getSubtype(game)) {
                if (subType.getSubTypeSet() == SubTypeSet.CreatureType && input.getObject().hasSubtype(subType, game)) {
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public String toString() {
        return "shares a creature type";
    }
}
