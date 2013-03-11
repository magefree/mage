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
package mage.sets.darksteel;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public class SunderingTitan extends CardImpl<SunderingTitan> {

    public SunderingTitan(UUID ownerId) {
        super(ownerId, 146, "Sundering Titan", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");
        this.expansionSetCode = "DST";
        this.subtype.add("Golem");

        this.power = new MageInt(7);
        this.toughness = new MageInt(10);

        // When Sundering Titan enters the battlefield or leaves the battlefield, choose a land of each basic land type, then destroy those lands.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SunderingTitanDestroyLandEffect(), false));
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new SunderingTitanDestroyLandEffect(), false));
    }

    public SunderingTitan(final SunderingTitan card) {
        super(card);
    }

    @Override
    public SunderingTitan copy() {
        return new SunderingTitan(this);
    }
}

class SunderingTitanDestroyLandEffect extends OneShotEffect<SunderingTitanDestroyLandEffect> {

    public SunderingTitanDestroyLandEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "choose a land of each basic land type, then destroy those lands";
    }

    public SunderingTitanDestroyLandEffect(final SunderingTitanDestroyLandEffect effect) {
        super(effect);
    }

    @Override
    public SunderingTitanDestroyLandEffect copy() {
        return new SunderingTitanDestroyLandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Set<UUID> lands = new HashSet<UUID>();
        if (controller != null && sourcePermanent != null) {
            for (String landName : new String[] {"Forest","Island","Mountain","Plains","Swamp"}) {
                FilterLandPermanent filter = new FilterLandPermanent(new StringBuilder(landName).append(" to destroy").toString());
                filter.add(new SupertypePredicate("Basic"));
                filter.add(new SubtypePredicate(landName));
                Target target = new TargetLandPermanent(1,1, filter, true);
                target.setRequired(true);
                if (target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                    controller.chooseTarget(outcome, target, source, game);
                    lands.add(target.getFirstTarget());
                }

            }
            if (!lands.isEmpty()) {
                int destroyedLands = 0;
                for (UUID landId: lands) {
                    Permanent land = game.getPermanent(landId);
                    if (land != null) {
                        if (land.destroy(source.getSourceId(), game, false)) {
                            destroyedLands++;
                        }
                    }
                }
                game.informPlayers(new StringBuilder(sourcePermanent.getName()).append(": ").append(destroyedLands).append(destroyedLands > 1 ? " lands were destroyed":"land was destroyed").toString());
            }

        }
        return false;
    }
}
