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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;



/**
 *
 * @author LevelX2
 */
public class DetentionSphere extends CardImpl<DetentionSphere> {

    static final protected FilterPermanent filter = new FilterNonlandPermanent("nonland permanent not named Detention Sphere");
    static {
        filter.add(Predicates.not(new NamePredicate("Detention Sphere")));
    }

    public DetentionSphere(UUID ownerId) {
        super(ownerId, 155, "Detention Sphere", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");
        this.expansionSetCode = "RTR";

        this.color.setWhite(true);
        this.color.setBlue(true);

        // When Detention Sphere enters the battlefield, you may exile
        // target nonland permanent not named Detention Sphere and all
        // other permanents with the same name as that permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DetentionSphereEntersEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);


        // When Detention Sphere leaves the battlefield, return the exiled
        // cards to the battlefield under their owner's control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new DetentionSphereLeavesEffect(), false));
    }

    public DetentionSphere(final DetentionSphere card) {
        super(card);
    }

    @Override
    public DetentionSphere copy() {
        return new DetentionSphere(this);
    }
}

class DetentionSphereEntersEffect extends OneShotEffect<DetentionSphereEntersEffect> {


    public DetentionSphereEntersEffect() {
        super(Outcome.Exile);
        staticText = "you may exile target nonland permanent not named Detention Sphere and all other permanents with the same name as that permanent";
    }

    public DetentionSphereEntersEffect(final DetentionSphereEntersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = source.getSourceId();
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (exileId != null && targetPermanent != null) {
            String name = targetPermanent.getName();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                if (permanent != null && permanent.getName().equals(name)) {
                    permanent.moveToExile(exileId, "Detention Sphere", source.getId(), game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public DetentionSphereEntersEffect copy() {
        return new DetentionSphereEntersEffect(this);
    }
}

class DetentionSphereLeavesEffect extends OneShotEffect<DetentionSphereLeavesEffect> {

    public DetentionSphereLeavesEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "return the exiled cards to the battlefield under their owner's control";
    }

    public DetentionSphereLeavesEffect(final DetentionSphereLeavesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = source.getSourceId();
        ExileZone exile = game.getExile().getExileZone(exileId);
        if (exile != null) {
            exile = exile.copy();
            for (UUID cardId : exile) {
                Card card = game.getCard(cardId);
                card.putOntoBattlefield(game, Constants.Zone.EXILED, source.getId(), card.getOwnerId());
            }
            game.getExile().getExileZone(exileId).clear();
            return true;
        }
        return false;
    }

    @Override
    public DetentionSphereLeavesEffect copy() {
        return new DetentionSphereLeavesEffect(this);
    }
}