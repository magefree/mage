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

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author North
 */
public class AuraFinesse extends CardImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Aura you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.AURA));
    }

    public AuraFinesse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Attach target Aura you control to target creature.
        this.getSpellAbility().addEffect(new AuraFinesseEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public AuraFinesse(final AuraFinesse card) {
        super(card);
    }

    @Override
    public AuraFinesse copy() {
        return new AuraFinesse(this);
    }
}

class AuraFinesseEffect extends OneShotEffect {

    public AuraFinesseEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Attach target Aura you control to target creature";
    }

    public AuraFinesseEffect(final AuraFinesseEffect effect) {
        super(effect);
    }

    @Override
    public AuraFinesseEffect copy() {
        return new AuraFinesseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent aura = game.getPermanent(source.getFirstTarget());
            Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (aura != null && creature != null) {
                Permanent oldCreature = game.getPermanent(aura.getAttachedTo());
                if (oldCreature != null && !oldCreature.equals(creature)) {
                    Target auraTarget = aura.getSpellAbility().getTargets().get(0);
                    if (!auraTarget.canTarget(creature.getId(), game))  {
                        game.informPlayers(aura.getLogName() + " was not attched to " +creature.getLogName() + " because it's no legal target for the aura" );
                    } else if (oldCreature.removeAttachment(aura.getId(), game)) {
                        game.informPlayers(aura.getLogName() + " was unattached from " + oldCreature.getLogName() + " and attached to " + creature.getLogName());
                        creature.addAttachment(aura.getId(), game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
