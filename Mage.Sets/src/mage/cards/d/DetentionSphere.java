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
package mage.cards.d;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author LevelX2
 */
public class DetentionSphere extends CardImpl {

    static final protected FilterPermanent filter = new FilterNonlandPermanent("nonland permanent not named Detention Sphere");

    static {
        filter.add(Predicates.not(new NamePredicate("Detention Sphere")));
    }

    public DetentionSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");

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

class DetentionSphereEntersEffect extends OneShotEffect {

    public DetentionSphereEntersEffect() {
        super(Outcome.Exile);
        staticText = "you may exile target nonland permanent not named Detention Sphere and all other permanents with the same name as that permanent";
    }

    public DetentionSphereEntersEffect(final DetentionSphereEntersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && exileId != null && targetPermanent != null && controller != null) {

            if (targetPermanent.getName().isEmpty()) { // face down creature
                controller.moveCardToExileWithInfo(targetPermanent, exileId, sourceObject.getIdName(), source.getSourceId(), game, Zone.BATTLEFIELD, true);
            } else {
                String name = targetPermanent.getName();
                for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                    if (permanent != null && permanent.getName().equals(name)) {
                        controller.moveCardToExileWithInfo(permanent, exileId, sourceObject.getIdName(), source.getSourceId(), game, Zone.BATTLEFIELD, true);
                    }
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

class DetentionSphereLeavesEffect extends OneShotEffect {

    public DetentionSphereLeavesEffect() {
        super(Outcome.Neutral);
        staticText = "return the exiled cards to the battlefield under their owner's control";
    }

    public DetentionSphereLeavesEffect(final DetentionSphereLeavesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            Permanent permanentLeftBattlefield = (Permanent) getValue("permanentLeftBattlefield");
            if (permanentLeftBattlefield == null) {
                Logger.getLogger(ReturnFromExileForSourceEffect.class).error("Permanent not found: " + sourceObject.getName());
                return false;
            }
            ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), permanentLeftBattlefield.getZoneChangeCounter(game)));
            if (exile != null) {
                controller.moveCards(exile.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
            return true;
        }
        return false;
    }

    @Override
    public DetentionSphereLeavesEffect copy() {
        return new DetentionSphereLeavesEffect(this);
    }
}
