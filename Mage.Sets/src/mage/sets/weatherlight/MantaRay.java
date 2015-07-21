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
package mage.sets.weatherlight;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerControlsIslandPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public class MantaRay extends CardImpl {

    public MantaRay(UUID ownerId) {
        super(ownerId, 42, "Manta Ray", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "WTH";
        this.subtype.add("Fish");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Manta Ray can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent("Island","an Island"))));
        
        // Manta Ray can't be blocked except by blue creatures.
        this.addAbility(MantaRayAbility.getInstance());
        
        // When you control no Islands, sacrifice Manta Ray.
        this.addAbility(new MantaRayTriggeredAbility());
    }

    public MantaRay(final MantaRay card) {
        super(card);
    }

    @Override
    public MantaRay copy() {
        return new MantaRay(this);
    }
}

class MantaRayTriggeredAbility extends StateTriggeredAbility {

    public MantaRayTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public MantaRayTriggeredAbility(final MantaRayTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MantaRayTriggeredAbility copy() {
        return new MantaRayTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return (game.getBattlefield().countAll(ControllerControlsIslandPredicate.filter, controllerId, game) == 0);
    }

    @Override
    public String getRule() {
        return "When you control no islands, sacrifice {this}.";
    }
}

class MantaRayAbility extends EvasionAbility {

    private static MantaRayAbility instance;

    public static MantaRayAbility getInstance() {
        if (instance == null) {
            instance = new MantaRayAbility();
        }
        return instance;
    }

    private MantaRayAbility() {
        this.addEffect(new MantaRayEffect());
    }

    @Override
    public String getRule() {
        return "{this} can't be blocked except by blue creatures.";
    }

    @Override
    public MantaRayAbility copy() {
        return getInstance();
    }
}

class MantaRayEffect extends RestrictionEffect {

    public MantaRayEffect() {
        super(Duration.WhileOnBattlefield);
    }

    public MantaRayEffect(final MantaRayEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getAbilities().containsKey(MantaRayAbility.getInstance().getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (blocker.getColor(game).isBlue()) {
            return true;
        }
        return false;
    }

    @Override
    public MantaRayEffect copy() {
        return new MantaRayEffect(this);
    }
}