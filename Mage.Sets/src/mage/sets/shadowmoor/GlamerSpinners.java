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

import java.util.LinkedList;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class GlamerSpinners extends CardImpl {

    public GlamerSpinners(UUID ownerId) {
        super(ownerId, 141, "Glamer Spinners", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{W/U}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Faerie");
        this.subtype.add("Wizard");

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Glamer Spinners enters the battlefield, attach all Auras enchanting target permanent to another permanent with the same controller.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GlamerSpinnersEffect(), false);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

    }

    public GlamerSpinners(final GlamerSpinners card) {
        super(card);
    }

    @Override
    public GlamerSpinners copy() {
        return new GlamerSpinners(this);
    }
}

class GlamerSpinnersEffect extends OneShotEffect {

    public GlamerSpinnersEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "attach all Auras enchanting target permanent to another permanent with the same controller";
    }

    public GlamerSpinnersEffect(final GlamerSpinnersEffect effect) {
        super(effect);
    }

    @Override
    public GlamerSpinnersEffect copy() {
        return new GlamerSpinnersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        /*
         5/1/2008 	When Glamer Spinners enters the battlefield, you target only one permanent: the one that will be losing its Auras. You don't choose the permanent that will be receiving the Auras until the ability resolves.
         5/1/2008 	You may target a permanent that has no Auras enchanting it.
         5/1/2008 	When the ability resolves, you choose the permanent that will be receiving the Auras. It can't be the targeted permanent, it must have the same controller as the targeted permanent, and it must be able to be enchanted by all the Auras attached to the targeted permanent. If you can't choose a permanent that meets all those criteria, the Auras won't move.
         */
        Boolean passed = true;
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPermanent != null) {
            FilterPermanent filterChoice = new FilterPermanent("a different permanent with the same controller as the target to attach the enchantments to");
            filterChoice.add(new ControllerIdPredicate(targetPermanent.getControllerId()));
            filterChoice.add(Predicates.not(new PermanentIdPredicate(targetPermanent.getId())));

            Target chosenPermanentToAttachAuras = new TargetPermanent(filterChoice);
            chosenPermanentToAttachAuras.setNotTarget(true);

            LinkedList<UUID> auras = new LinkedList();
            auras.addAll(targetPermanent.getAttachments());

            if (controller != null
                    && controller.choose(Outcome.Neutral, chosenPermanentToAttachAuras, source.getSourceId(), game)) {
                Permanent permanentToAttachAuras = game.getPermanent(chosenPermanentToAttachAuras.getFirstTarget());
                if (permanentToAttachAuras != null) {
                    for (UUID auraId : auras) {
                        Permanent aura = game.getPermanent(auraId);
                        if (aura != null
                                && passed) {
                            // Check the target filter
                            Target target = aura.getSpellAbility().getTargets().get(0);
                            if (target instanceof TargetPermanent) {
                                if (!target.getFilter().match(permanentToAttachAuras, game)) {
                                    passed = false;
                                }
                            }
                            // Check for protection
                            MageObject auraObject = game.getObject(auraId);
                            if (permanentToAttachAuras.cantBeEnchantedBy(auraObject, game)) {
                                passed = false;
                            }
                        }
                    }
                    if (passed) {
                        LinkedList<UUID> aurasToAttach = new LinkedList();
                        aurasToAttach.addAll(auras);

                        for (UUID auraId : aurasToAttach) {
                            Permanent auraToAttachToPermanent = game.getPermanent(auraId);
                            targetPermanent.removeAttachment(auraToAttachToPermanent.getId(), game);
                            permanentToAttachAuras.addAttachment(auraToAttachToPermanent.getId(), game);
                        }
                        return true;
                    }
                }
            }
        }
        game.informPlayers("Glamer Spinners: No enchantments were moved from the target permanent.");
        return false;
    }
}
