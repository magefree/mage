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
package mage.cards.c;

import java.util.LinkedList;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BlocksOrBecomesBlockedTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author rscoates
 */
public class CorrosiveOoze extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("equipped creature");

    static {
        filter.add(new EquippedPredicate());
    }

    public CorrosiveOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.OOZE);

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Corrosive Ooze blocks or becomes blocked by an equipped creature, destroy all Equipment attached to that creature at end of combat.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new CorrosiveOozeEffect()), true);
        this.addAbility(new BlocksOrBecomesBlockedTriggeredAbility(effect, filter, false));
    }

    public CorrosiveOoze(final CorrosiveOoze card) {
        super(card);
    }

    @Override
    public CorrosiveOoze copy() {
        return new CorrosiveOoze(this);
    }
}

class CorrosiveOozeEffect extends OneShotEffect {

    public CorrosiveOozeEffect() {
        super(Outcome.DestroyPermanent);
    }

    public CorrosiveOozeEffect(final CorrosiveOozeEffect effect) {
        super(effect);
    }

    @Override
    public CorrosiveOozeEffect copy() {
        return new CorrosiveOozeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            LinkedList<UUID> attachments = new LinkedList();
            attachments.addAll(permanent.getAttachments());
            for (UUID uuid : attachments) {
                Permanent attachment = game.getPermanent(uuid);
                if (attachment != null && attachment.hasSubtype(SubType.EQUIPMENT, game)) {
                    attachment.destroy(source.getSourceId(), game, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "destroy all Equipment attached to that creature at end of combat";
    }

}
