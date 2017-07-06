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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class GildedCerodon extends CardImpl {
    
    private static final String rule = "Whenever {this} attacks, if you control a Desert or there is a Desert card in your graveyard, target creature can't block this turn.";

    public GildedCerodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        
        this.subtype.add("Beast");
        
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Gilded Cerodon attacks, if you control a Desert or there is a Desert card in your graveyard, target creature can't block this turn.
        Ability ability = new ConditionalTriggeredAbility(new AttacksTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn), false), new GildedCerodonCondition(), rule);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
    }

    public GildedCerodon(final GildedCerodon card) {
        super(card);
    }

    @Override
    public GildedCerodon copy() {
        return new GildedCerodon(this);
    }
}

class GildedCerodonCondition implements Condition {
    
    private static final FilterPermanent filter = new FilterPermanent();
    private static final FilterCard filter2 = new FilterCard();
    
    static {
        filter.add(new SubtypePredicate(SubType.DESERT));
        filter2.add(new SubtypePredicate(SubType.DESERT));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && !game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game).isEmpty()
                || controller.getGraveyard().count(filter2, game) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "if you control a Desert or there is a Desert card in your graveyard";
    }

}
