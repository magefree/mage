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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class TuktukScrapper extends CardImpl {

    public TuktukScrapper(UUID ownerId) {
        super(ownerId, 94, "Tuktuk Scrapper", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Goblin");
        this.subtype.add("Artificer");
        this.subtype.add("Ally");

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

class TuktukScrapperTriggeredAbility extends TriggeredAbilityImpl {

    public TuktukScrapperTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TuktukScrapperEffect(), true);
        this.addTarget(new TargetArtifactPermanent());
    }

    public TuktukScrapperTriggeredAbility(final TuktukScrapperTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TuktukScrapperTriggeredAbility copy() {
        return new TuktukScrapperTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            if (permanent.getId() == this.getSourceId()) {
                return true;
            }
            if (permanent.hasSubtype("Ally")
                    && permanent.getControllerId().equals(this.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another Ally enters the battlefield under your control, you may destroy target artifact. If that artifact is put into a graveyard this way, {this} deals damage to that artifact's controller equal to the number of Allies you control.";
    }
}

class TuktukScrapperEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new SubtypePredicate("Ally"));
    }

    public TuktukScrapperEffect() {
        super(Outcome.DestroyPermanent);
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
        Permanent targetArtifact = game.getPermanent(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && targetArtifact != null) {
            targetArtifact.destroy(source.getSourceId(), game, false);
            Player targetController = game.getPlayer(targetArtifact.getControllerId());
            if (targetController != null && game.getState().getZone(targetArtifact.getId()).equals(Zone.GRAVEYARD)) {
                int alliesControlled = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
                if (alliesControlled > 0) {
                    targetController.damage(alliesControlled, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
