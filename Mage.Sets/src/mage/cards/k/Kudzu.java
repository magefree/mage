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
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class Kudzu extends CardImpl {

    public Kudzu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When enchanted land becomes tapped, destroy it. That land's controller attaches Kudzu to a land of his or her choice.
        this.addAbility(new BecomesTappedAttachedTriggeredAbility(new KudzuEffect(), "enchanted land"));

    }

    public Kudzu(final Kudzu card) {
        super(card);
    }

    @Override
    public Kudzu copy() {
        return new Kudzu(this);
    }
}

class KudzuEffect extends OneShotEffect {

    public KudzuEffect() {
        super(Outcome.Detriment);
        staticText = "destroy it. That land's controller attaches {this} to a land of his or her choice";
    }

    public KudzuEffect(final KudzuEffect effect) {
        super(effect);
    }

    @Override
    public KudzuEffect copy() {
        return new KudzuEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent kudzu = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Card kudzuCard = game.getCard(source.getSourceId());
        if (kudzu != null) {
            Permanent enchantedLand = game.getPermanentOrLKIBattlefield(kudzu.getAttachedTo());
            Player controller = game.getPlayer(source.getControllerId());
            if (enchantedLand != null
                    && controller != null) {
                Player landsController = game.getPlayer(enchantedLand.getControllerId());
                if (game.getState().getZone(enchantedLand.getId()) == Zone.BATTLEFIELD) { // if 2 or more Kudzu's were on a land
                    enchantedLand.destroy(source.getId(), game, false);
                }
                if (!game.getBattlefield().getAllActivePermanents(CardType.LAND).isEmpty()) { //lands are available on the battlefield
                    Target target = new TargetLandPermanent();
                    target.setNotTarget(true); //not a target, it is chosen
                    if (kudzuCard != null
                            && landsController != null) {
                        if (landsController.choose(Outcome.Detriment, target, source.getId(), game)) {
                            if (target.getFirstTarget() != null) {
                                Permanent landChosen = game.getPermanent(target.getFirstTarget());
                                if (landChosen != null) {
                                    for (Target targetTest : kudzuCard.getSpellAbility().getTargets()) {
                                        Filter filterTest = targetTest.getFilter();
                                        if (filterTest.match(landChosen, game)) {
                                            if (game.getBattlefield().containsPermanent(landChosen.getId())) { //verify that it is still on the battlefield
                                                game.getState().setValue("attachTo:" + kudzuCard.getId(), landChosen);
                                                Zone zone = game.getState().getZone(kudzuCard.getId());
                                                kudzuCard.putOntoBattlefield(game, zone, source.getSourceId(), controller.getId());
                                                return landChosen.addAttachment(kudzuCard.getId(), game);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
