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
package mage.sets.lorwyn;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ChampionAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class MistbindClique extends CardImpl<MistbindClique> {

    public MistbindClique(UUID ownerId) {
        super(ownerId, 75, "Mistbind Clique", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Faerie");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Champion a Faerie
        this.addAbility(new ChampionAbility(this, "Faerie"));

        // When a Faerie is championed with Mistbind Clique, tap all lands target player controls.
        this.addAbility(new MistbindCliqueAbility());


    }

    public MistbindClique(final MistbindClique card) {
        super(card);
    }

    @Override
    public MistbindClique copy() {
        return new MistbindClique(this);
    }
}

class MistbindCliqueAbility extends ZoneChangeTriggeredAbility<MistbindCliqueAbility> {

    public MistbindCliqueAbility() {
        super(Zone.BATTLEFIELD, Zone.EXILED, new MistbindCliqueTapEffect(), "When a Faerie is championed with {this}, ", false);
        this.addTarget(new TargetPlayer());
    }

    public MistbindCliqueAbility(MistbindCliqueAbility ability) {
        super(ability);
    }

    @Override
    public MistbindCliqueAbility copy() {
        return new MistbindCliqueAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getSourceId() != null && event.getSourceId().equals(getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.EXILED) {
                MageObject object = game.getObject(event.getTargetId());
                if (object != null && object.getSubtype().contains("Faerie")) {
                    return true;
                }
            }
        }
        return false;
    }
}

class MistbindCliqueTapEffect extends OneShotEffect<MistbindCliqueTapEffect> {

    public MistbindCliqueTapEffect() {
        super(Outcome.Tap);
        staticText = "tap all lands target player controls";
    }

    public MistbindCliqueTapEffect(final MistbindCliqueTapEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getFirstTarget() == null) {
            return false;
        }

        FilterLandPermanent filter = new FilterLandPermanent();
        filter.add(new ControllerIdPredicate(source.getFirstTarget()));

        List<Permanent> lands = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
        for (Permanent land : lands) {
            land.tap(game);
        }
        return true;
    }

    @Override
    public MistbindCliqueTapEffect copy() {
        return new MistbindCliqueTapEffect(this);
    }
}
