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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CantBlockAttackActivateAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class PrisonTerm extends CardImpl<PrisonTerm> {
    
    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature");
    
    static {
        filter.add(new ControllerPredicate(Constants.TargetController.OPPONENT));
    }

    public PrisonTerm(UUID ownerId) {
        super(ownerId, 18, "Prison Term", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Aura");

        this.color.setWhite(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't attack or block, and its activated abilities can't be activated.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockAttackActivateAttachedEffect()));

        // Whenever a creature enters the battlefield under an opponent's control, you may attach Prison Term to that creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new PrisonTermEffect(), filter, true, true, "Whenever a creature enters the battlefield under an opponent's control, you may attach Prison Term to that creature."));
    }

    public PrisonTerm(final PrisonTerm card) {
        super(card);
    }

    @Override
    public PrisonTerm copy() {
        return new PrisonTerm(this);
    }
}

class PrisonTermEffect extends OneShotEffect<PrisonTermEffect> {

    public PrisonTermEffect() {
        super(Constants.Outcome.Detriment);
    }

    public PrisonTermEffect(final PrisonTermEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Permanent opponentCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent prisonTerm = game.getPermanent(source.getSourceId());
        if (you != null
                && opponentCreature != null
                && prisonTerm != null
                && opponentCreature.getCardType().contains(CardType.CREATURE)) {
            game.getState().setValue("attachTo:" + prisonTerm.getId(), opponentCreature);
            return opponentCreature.addAttachment(prisonTerm.getId(), game);
        }
        return false;
    }

    @Override
    public PrisonTermEffect copy() {
        return new PrisonTermEffect(this);
    }
}