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
package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public class AdmiralAckbar extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Starship creatures");

    static {
        filter.add(new SubtypePredicate("Starship"));
    }

    public AdmiralAckbar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Calamari");
        this.subtype.add("Rebel");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When you cast Admiral Ackbar, create two 2/3 blue Rebel Starship artifact creature tokens with spaceflight name B-Wing.
        this.addAbility(new CastSourceTriggeredAbility(new CreateTokenEffect(new RebelStarshipToken(), 2), false));

        // At the beggining of each upkeep, untap all starships you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new UntapAllControllerEffect(filter), TargetController.ANY, false));

        // Whenever two or more Starship creatures you control attack, draw a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new DrawCardSourceControllerEffect(1), 2, filter));
    }

    public AdmiralAckbar(final AdmiralAckbar card) {
        super(card);
    }

    @Override
    public AdmiralAckbar copy() {
        return new AdmiralAckbar(this);
    }
}

class RebelStarshipToken extends Token {

    public RebelStarshipToken() {
        super("B-Wing", "2/3 blue Rebel Starship artifact creature tokens with spaceflight name B-Wing", 2, 3);
        this.setOriginalExpansionSetCode("SWS");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        abilities.add(SpaceflightAbility.getInstance());
        color.setBlue(true);
        subtype.add("Rebel");
        subtype.add("Starship");
    }
}

class AdmiralAckbarTriggeredAbility extends TriggeredAbilityImpl {

    public AdmiralAckbarTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public AdmiralAckbarTriggeredAbility(final AdmiralAckbarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AdmiralAckbarTriggeredAbility copy() {
        return new AdmiralAckbarTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().size() >= 2 && game.getCombat().getAttackerId().equals(getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever two or more Starship creatures you control attack, draw a card" ;
    }
}
