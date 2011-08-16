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
package mage.sets.magic2012;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class Worldslayer extends CardImpl<Worldslayer> {

    public Worldslayer(UUID ownerId) {
        super(ownerId, 222, "Worldslayer", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "M12";
        this.subtype.add("Equipment");

        this.addAbility(new WorldslayerTriggeredAbility());
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(5)));
    }

    public Worldslayer(final Worldslayer card) {
        super(card);
    }

    @Override
    public Worldslayer copy() {
        return new Worldslayer(this);
    }
}

class WorldslayerTriggeredAbility extends TriggeredAbilityImpl<WorldslayerTriggeredAbility> {

    WorldslayerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WorldslayerEffect(), false);
    }

    WorldslayerTriggeredAbility(final WorldslayerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WorldslayerTriggeredAbility copy() {
        return new WorldslayerTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, destroy all permanents other than {this}.";
    }
}

class WorldslayerEffect extends OneShotEffect {

    public WorldslayerEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy all permanents other than {this}";
    }

    public WorldslayerEffect(final WorldslayerEffect effect) {
        super(effect);
    }

    @Override
    public WorldslayerEffect copy() {
        return new WorldslayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            if (permanent.getId() != source.getSourceId()) {
                permanent.destroy(source.getId(), game, false);
            }
        }
        return true;
    }
}
