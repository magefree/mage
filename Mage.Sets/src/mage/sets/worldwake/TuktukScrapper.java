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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class TuktukScrapper extends CardImpl<TuktukScrapper> {

    public TuktukScrapper(UUID ownerId) {
        super(ownerId, 94, "Tuktuk Scrapper", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Goblin");
        this.subtype.add("Artificer");
        this.subtype.add("Ally");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Tuktuk Scrapper or another Ally enters the battlefield under your control, you may destroy target artifact. If that artifact is put into a graveyard this way, Tuktuk Scrapper deals damage to that artifact's controller equal to the number of Allies you control.
        this.addAbility(new TuktukScrapperTriggeredAbility());
    }

    public TuktukScrapper(final TuktukScrapper card) {
        super(card);
    }

    @Override
    public TuktukScrapper copy() {
        return new TuktukScrapper(this);
    }
}

class TuktukScrapperTriggeredAbility extends TriggeredAbilityImpl<TuktukScrapperTriggeredAbility> {

    public TuktukScrapperTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new TuktukScrapperEffect(), true);
    }

    public TuktukScrapperTriggeredAbility(final TuktukScrapperTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TuktukScrapperTriggeredAbility copy() {
        return new TuktukScrapperTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Constants.Zone.BATTLEFIELD) {
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent != null && permanent.getId() == this.getSourceId()) {
                    return true;
                }
                if (permanent != null
                        && permanent.hasSubtype("Ally")
                        && permanent.getControllerId().equals(this.getControllerId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another Ally enters the battlefield under your control, you may destroy target artifact. If that artifact is put into a graveyard this way, {this} deals damage to that artifact's controller equal to the number of Allies you control.";
    }
}

class TuktukScrapperEffect extends OneShotEffect<TuktukScrapperEffect> {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new SubtypePredicate("Ally"));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public TuktukScrapperEffect() {
        super(Constants.Outcome.DestroyPermanent);
    }

    public TuktukScrapperEffect(final TuktukScrapperEffect effect) {
        super(effect);
    }

    @Override
    public TuktukScrapperEffect copy() {
        return new TuktukScrapperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetArtifactPermanent target = new TargetArtifactPermanent();
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            if (target.canChoose(source.getControllerId(), game) && target.choose(Constants.Outcome.DestroyPermanent, source.getControllerId(), source.getId(), game)) {
                Permanent targetedArtifact = game.getPermanent(target.getFirstTarget());
                if (targetedArtifact != null) {
                    Card artifact = game.getCard(targetedArtifact.getId());
                    Player controller = game.getPlayer(targetedArtifact.getControllerId());
                    targetedArtifact.destroy(id, game, true);
                    if (controller.getGraveyard().contains(artifact.getId())) {
                        int alliesControlled = game.getBattlefield().count(filter, source.getControllerId(), game);
                        controller.damage(alliesControlled, id, game, false, true);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
