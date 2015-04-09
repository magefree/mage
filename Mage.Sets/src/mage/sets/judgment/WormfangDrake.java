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

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author Temba21
 */
public class WormfangDrake extends CardImpl {
    

    
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
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new WormfangDrakeExileCost()), false));

        // When Wormfang Drake leaves the battlefield, return the exiled card to the battlefield under its owner's control.       
        this.addAbility(new WormfangDrakeTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false));
    }

    public WormfangDrake(final WormfangDrake card) {
        super(card);
    }

    @Override
    public WormfangDrake copy() {
        return new WormfangDrake(this);
    }
}

class WormfangDrakeTriggeredAbility extends ZoneChangeTriggeredAbility {

    public WormfangDrakeTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, null, effect, "When {this} leaves the battlefield, ", optional);
    }

    public WormfangDrakeTriggeredAbility(WormfangDrakeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WormfangDrakeTriggeredAbility copy() {
        return new WormfangDrakeTriggeredAbility(this);
    }

}

class WormfangDrakeExileCost extends CostImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new AnotherPredicate());
    }

    public WormfangDrakeExileCost() {
        this.addTarget(new TargetControlledCreaturePermanent(1,1,filter, true));
        this.text = "Exile a creature you control other than {this}";
    }

    public WormfangDrakeExileCost(WormfangDrakeExileCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Player controller = game.getPlayer(controllerId);
        MageObject sourceObject = ability.getSourceObject(game);        
        if (controller != null && sourceObject != null) {
            if (targets.choose(Outcome.Exile, controllerId, sourceId, game)) {
                UUID exileId = CardUtil.getExileZoneId(game, ability.getSourceId(), ability.getSourceObjectZoneChangeCounter());
                for (UUID targetId: targets.get(0).getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent == null) {
                        return false;
                    }
                    paid |= controller.moveCardToExileWithInfo(permanent, exileId, sourceObject.getLogName() + " exiled permanents", sourceId, game, Zone.BATTLEFIELD, true);
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
