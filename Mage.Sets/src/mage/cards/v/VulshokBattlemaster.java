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
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Derpthemeus
 */
public class VulshokBattlemaster extends CardImpl {

    public VulshokBattlemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // When Vulshok Battlemaster enters the battlefield, attach all Equipment on the battlefield to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VulshokBattlemasterEffect()));
    }

    public VulshokBattlemaster(final VulshokBattlemaster card) {
        super(card);
    }

    @Override
    public VulshokBattlemaster copy() {
        return new VulshokBattlemaster(this);
    }

    static class VulshokBattlemasterEffect extends OneShotEffect {

        public VulshokBattlemasterEffect() {
            super(Outcome.Benefit);
            this.staticText = "attach all Equipment on the battlefield to it";
        }

        public VulshokBattlemasterEffect(final VulshokBattlemasterEffect effect) {
            super(effect);
        }

        @Override
        public VulshokBattlemasterEffect copy() {
            return new VulshokBattlemasterEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent battlemaster = game.getPermanent(source.getSourceId());
            if (battlemaster != null) {
                FilterPermanent filter = new FilterPermanent();
                filter.add(new SubtypePredicate(SubType.EQUIPMENT));
                for (Permanent equipment : game.getBattlefield().getAllActivePermanents(filter, game)) {
                    if (equipment != null) {
                        //If an Equipment can’t equip Vulshok Battlemaster, it isn’t attached to the Battlemaster, and it doesn’t become unattached (if it’s attached to a creature). (http://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=48125)
                        if (!battlemaster.cantBeAttachedBy(equipment, game)) {
                            battlemaster.addAttachment(equipment.getId(), game);
                        }
                    }
                }
                return true;
            }
            return false;
        }
    }
}
