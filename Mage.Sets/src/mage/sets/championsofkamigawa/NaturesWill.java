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
package mage.sets.championsofkamigawa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class NaturesWill extends CardImpl<NaturesWill> {

    public NaturesWill(UUID ownerId) {
        super(ownerId, 230, "Nature's Will", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");
        this.expansionSetCode = "CHK";

        this.color.setGreen(true);

        // Whenever one or more creatures you control deal combat damage to a player, tap all lands that player controls and untap all lands you control.
        this.addAbility(new NaturesWillTriggeredAbility());
    }

    public NaturesWill(final NaturesWill card) {
        super(card);
    }

    @Override
    public NaturesWill copy() {
        return new NaturesWill(this);
    }
}

class NaturesWillTriggeredAbility extends TriggeredAbilityImpl<NaturesWillTriggeredAbility> {

    private boolean madeDamge = false;
    private Set<UUID> damagedPlayers = new HashSet<UUID>();

    public NaturesWillTriggeredAbility() {
        super(Zone.BATTLEFIELD, new NaturesWillEffect(), false);
    }

    public NaturesWillTriggeredAbility(final NaturesWillTriggeredAbility ability) {
        super(ability);
        this.madeDamge = ability.madeDamge;
        this.damagedPlayers = new HashSet<UUID>();
        this.damagedPlayers.addAll(ability.damagedPlayers);
    }

    @Override
    public NaturesWillTriggeredAbility copy() {
        return new NaturesWillTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.getControllerId().equals(this.getControllerId())) {
                madeDamge = true;
                damagedPlayers.add(event.getPlayerId());
            }
        }
        if (event.getType().equals(GameEvent.EventType.COMBAT_DAMAGE_STEP_POST)) {
            if (madeDamge) {
                Set<UUID> damagedPlayersCopy = new HashSet<UUID>();
                damagedPlayersCopy.addAll(damagedPlayers);
                for(Effect effect: this.getEffects()) {
                    effect.setValue("damagedPlayers", damagedPlayersCopy);
                }
                damagedPlayers.clear();
                madeDamge = false;
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control deal combat damage to a player, " + super.getRule();
    }
}

class NaturesWillEffect extends OneShotEffect<NaturesWillEffect> {

    public NaturesWillEffect() {
        super(Outcome.Benefit);
        this.staticText = "tap all lands that player controls and untap all lands you control";
    }

    public NaturesWillEffect(final NaturesWillEffect effect) {
        super(effect);
    }

    @Override
    public NaturesWillEffect copy() {
        return new NaturesWillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> damagedPlayers = (HashSet<UUID>) this.getValue("damagedPlayers");
        if (damagedPlayers == null) {
            return false;
        }

        FilterLandPermanent filter = new FilterLandPermanent();
        List<Permanent> lands = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
        for (Permanent land : lands) {
            if (damagedPlayers.contains(land.getControllerId())) {
                land.tap(game);
            } else if (land.getControllerId().equals(source.getControllerId())) {
                land.untap(game);
            }
        }


        return false;
    }
}
