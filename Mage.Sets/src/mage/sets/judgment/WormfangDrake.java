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
package mage.sets.judgment;

import java.util.ArrayList;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

/**
 *
 * @author Temba21
 */
public class WormfangDrake extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    
    static {
        filter.add(Predicates.not(new NamePredicate("Wormfang Drake")));
    }
    
    public WormfangDrake(UUID ownerId) {
        super(ownerId, 57, "Wormfang Drake", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "JUD";
        this.subtype.add("Nightmare");
        this.subtype.add("Drake");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
    
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Wormfang Drake enters the battlefield, sacrifice it unless you exile a creature you control other than Wormfang Drake.     
        // When Wormfang Drake leaves the battlefield, return the exiled card to the battlefield under its owner's control.       
        this.addAbility(new WormfangDrakeAbility(this, CardType.CREATURE));
    }

    public WormfangDrake(final WormfangDrake card) {
        super(card);
    }

    @Override
    public WormfangDrake copy() {
        return new WormfangDrake(this);
    }
}

class WormfangDrakeAbility extends StaticAbility {

    protected CardType cardType;
    protected String objectDescription;

    public WormfangDrakeAbility(Card card, CardType cardtypes) {
        super(Zone.BATTLEFIELD, null);

        this.cardType = cardtypes;

        StringBuilder sb = new StringBuilder("another ");
        ArrayList<Predicate<MageObject>> cardtypesPredicates = new ArrayList<>();
        cardtypesPredicates.add(new CardTypePredicate(cardType));
        sb.append(cardType);

        this.objectDescription = sb.toString();
        FilterControlledPermanent filter = new FilterControlledPermanent(objectDescription);
        filter.add(Predicates.or(cardtypesPredicates));
        filter.add(new AnotherPredicate());

        // When Wormfang Drake enters the battlefield, sacrifice it unless you exile another creature you control.
        Ability ability1 = new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new WormfangDrakeExileCost(filter, new StringBuilder(card.getName()).append(" WormfangDrakeed permanents").toString())),false);
        ability1.setRuleVisible(false);
        card.addAbility(ability1);

        // When this permanent leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false);
        ability2.setRuleVisible(false);
        card.addAbility(ability2);
    }

    public WormfangDrakeAbility(final WormfangDrakeAbility ability) {
        super(ability);
        this.cardType = ability.cardType;
        this.objectDescription = ability.objectDescription;
    }

    @Override
    public WormfangDrakeAbility copy() {
        return new WormfangDrakeAbility(this);
    }
}

class WormfangDrakeExileCost extends CostImpl {

    private String exileZone = null;

    public WormfangDrakeExileCost(FilterControlledPermanent filter, String exileZone) {
        this.addTarget(new TargetControlledPermanent(1,1,filter, true));
        this.text = "exile " + filter.getMessage() + " you control";
        this.exileZone = exileZone;
    }

    public WormfangDrakeExileCost(WormfangDrakeExileCost cost) {
        super(cost);
        this.exileZone = cost.exileZone;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Player controller = game.getPlayer(controllerId);
        MageObject sourceObject = ability.getSourceObject(game);        
        if (controller != null && sourceObject != null) {
            if (targets.choose(Outcome.Exile, controllerId, sourceId, game)) {
                UUID exileId = CardUtil.getObjectExileZoneId(game, sourceObject);
                for (UUID targetId: targets.get(0).getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent == null) {
                        return false;
                    }
                    paid |= controller.moveCardToExileWithInfo(permanent, exileId, sourceObject.getLogName() + " exiled permanents", sourceId, game, Zone.BATTLEFIELD);
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public WormfangDrakeExileCost copy() {
        return new WormfangDrakeExileCost(this);
    }
}
