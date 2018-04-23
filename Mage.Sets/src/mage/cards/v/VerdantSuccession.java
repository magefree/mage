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
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public class VerdantSuccession extends CardImpl {

    public VerdantSuccession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        // Whenever a green nontoken creature dies, that creature's controller may search their library for a card with the same name as that creature and put it onto the battlefield. If that player does, he or she shuffles their library.
        this.addAbility(new VerdantSuccessionTriggeredAbility());

    }

    public VerdantSuccession(final VerdantSuccession card) {
        super(card);
    }

    @Override
    public VerdantSuccession copy() {
        return new VerdantSuccession(this);
    }
}

class VerdantSuccessionTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("green nontoken creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public VerdantSuccessionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new VerdantSuccessionEffect());
        this.optional = true;
    }

    public VerdantSuccessionTriggeredAbility(final VerdantSuccessionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VerdantSuccessionTriggeredAbility copy() {
        return new VerdantSuccessionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            MageObject mageObject = game.getObject(sourceId);
            if (permanent != null
                    && filter.match(permanent, game)) {
                game.getState().setValue("verdantSuccession" + mageObject, permanent);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a green nontoken creature dies, that creature's controller may search their library for a card with the same name as that creature and put it onto the battlefield. If that player does, he or she shuffles their library.";
    }
}

class VerdantSuccessionEffect extends OneShotEffect {

    private Permanent permanent;

    VerdantSuccessionEffect() {
        super(Outcome.PutCardInPlay);
    }

    VerdantSuccessionEffect(final VerdantSuccessionEffect effect) {
        super(effect);
    }

    @Override
    public VerdantSuccessionEffect copy() {
        return new VerdantSuccessionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getObject(source.getSourceId());
        permanent = (Permanent) game.getState().getValue("verdantSuccession" + mageObject);
        if (permanent != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if (controller != null) {
                FilterCard filterCard = new FilterCard("Card named " + permanent.getName());
                filterCard.add(new NamePredicate(permanent.getName()));
                TargetCardInLibrary target = new TargetCardInLibrary(filterCard);
                controller.searchLibrary(target, game);
                if (!target.getTargets().isEmpty()) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null
                            && controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                        controller.shuffleLibrary(source, game);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
