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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class GodoBanditWarlord extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Equipment card");
    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new SubtypePredicate(SubType.EQUIPMENT));
    }

    public GodoBanditWarlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Barbarian");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Godo, Bandit Warlord enters the battlefield, you may search your library for an Equipment card and put it onto the battlefield. If you do, shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false, true), true));
        // Whenever Godo attacks for the first time each turn, untap it and all Samurai you control. After this phase, there is an additional combat phase.
        FilterControlledCreaturePermanent untapFilter = new FilterControlledCreaturePermanent();
        untapFilter.add(Predicates.or(new PermanentIdPredicate(this.getId()), new SubtypePredicate(SubType.SAMURAI)));
        Ability ability = new GodoBanditWarlordAttacksTriggeredAbility(new UntapAllControllerEffect(untapFilter,"untap it and all Samurai you control"), false);
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(ability);
    }

    public GodoBanditWarlord(final GodoBanditWarlord card) {
        super(card);
    }

    @Override
    public GodoBanditWarlord copy() {
        return new GodoBanditWarlord(this);
    }
}

class GodoBanditWarlordAttacksTriggeredAbility extends TriggeredAbilityImpl {

    public GodoBanditWarlordAttacksTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public GodoBanditWarlordAttacksTriggeredAbility(final GodoBanditWarlordAttacksTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public void reset(Game game) {
        game.getState().setValue(CardUtil.getCardZoneString("amountAttacks", this.getSourceId(), game), 0);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
       if (event.getSourceId().equals(this.getSourceId()) ) {
           Integer amountAttacks = (Integer) game.getState().getValue(CardUtil.getCardZoneString("amountAttacks", this.getSourceId(), game));
           if (amountAttacks == null || amountAttacks < 1) {
               if (amountAttacks == null) {
                   amountAttacks = 1;
               } else {
                   ++amountAttacks;
               }
               game.getState().setValue(CardUtil.getCardZoneString("amountAttacks", this.getSourceId(), game), amountAttacks);
               return true;
           }
       }
       return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks for the first time each turn, " + super.getRule();
    }

    @Override
    public GodoBanditWarlordAttacksTriggeredAbility copy() {
       return new GodoBanditWarlordAttacksTriggeredAbility(this);
    }
}

