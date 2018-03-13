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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.other.AuraCardCanAttachToLKIPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public class AuratouchedMage extends CardImpl {

    public AuratouchedMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Auratouched Mage enters the battlefield, search your library for an Aura card that could enchant it. If Auratouched Mage is still on the battlefield, put that Aura card onto the battlefield attached to it. Otherwise, reveal the Aura card and put it into your hand. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AuratouchedMageEffect(), false));

    }

    public AuratouchedMage(final AuratouchedMage card) {
        super(card);
    }

    @Override
    public AuratouchedMage copy() {
        return new AuratouchedMage(this);
    }
}

class AuratouchedMageEffect extends OneShotEffect {

    public AuratouchedMageEffect() {
        super(Outcome.BoostCreature);
        staticText = "search your library for an Aura card that could enchant it. If {this} is still on the battlefield, put that Aura card onto the battlefield attached to it. Otherwise, reveal the Aura card and put it into your hand. Then shuffle your library.";
    }

    public AuratouchedMageEffect(final AuratouchedMageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent auratouchedMage = game.getPermanentOrLKIBattlefield(source.getSourceId()); //must be LKI to resolve
        if (controller != null && auratouchedMage != null) {
            FilterCard filter = new FilterCard("aura that could enchant " + auratouchedMage.getName());
            filter.add(new SubtypePredicate(SubType.AURA));
            filter.add(new AuraCardCanAttachToLKIPermanentId(auratouchedMage.getId()));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            target.setNotTarget(true);
            if (controller.searchLibrary(target, game)) {
                if (target.getFirstTarget() != null) {
                    Card aura = game.getCard(target.getFirstTarget());
                    if (game.getBattlefield().containsPermanent(auratouchedMage.getId())) { //verify that it is still on the battlefield
                        game.getState().setValue("attachTo:" + aura.getId(), auratouchedMage);
                        aura.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), controller.getId());
                        return auratouchedMage.addAttachment(aura.getId(), game);
                    }
                    Cards auraRevealed = new CardsImpl();
                    auraRevealed.add(aura);
                    controller.revealCards(auratouchedMage.getName(), auraRevealed, game);
                    controller.putInHand(aura, game);
                }
            }
            controller.shuffleLibrary(source, game);
        }
        return false;
    }

    @Override
    public AuratouchedMageEffect copy() {
        return new AuratouchedMageEffect(this);
    }
}
