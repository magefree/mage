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
package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author spjspj
 */
public class EyeOfSingularity extends CardImpl {

    public EyeOfSingularity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.addSuperType(SuperType.WORLD);

        // When Eye of Singularity enters the battlefield, destroy each permanent with the same name as another permanent, except for basic lands. They can't be regenerated.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EyeOfSingularityETBEffect()));

        // Whenever a permanent other than a basic land enters the battlefield, destroy all other permanents with that name. They can't be regenerated.
        this.addAbility(new EyeOfSingularityTriggeredAbility());
    }

    public EyeOfSingularity(final EyeOfSingularity card) {
        super(card);
    }

    @Override
    public EyeOfSingularity copy() {
        return new EyeOfSingularity(this);
    }
}

class EyeOfSingularityETBEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
    }

    EyeOfSingularityETBEffect() {
        super(Outcome.Benefit);
        this.staticText = "destroy each permanent with the same name as another permanent, except for basic lands. They can't be regenerated";
    }

    EyeOfSingularityETBEffect(final EyeOfSingularityETBEffect effect) {
        super(effect);
    }

    @Override
    public EyeOfSingularityETBEffect copy() {
        return new EyeOfSingularityETBEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        HashMap<String, UUID> cardNames = new HashMap<>();
        HashMap<UUID, Integer> toDestroy = new HashMap<>();

        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            String cardName = permanent.getName();
            if (cardNames.get(cardName) == null) {
                cardNames.put(cardName, permanent.getId());
            } else {
                toDestroy.put(cardNames.get(cardName), 1);
                toDestroy.put(permanent.getId(), 1);
            }
        }
        for (UUID id : toDestroy.keySet()) {
            Permanent permanent = game.getPermanent(id);
            if (permanent != null) {
                permanent.destroy(source.getSourceId(), game, false);
            }
        }
        return true;
    }
}

class EyeOfSingularityTriggeredAbility extends TriggeredAbilityImpl {

    EyeOfSingularityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new EyeOfSingularityTriggeredEffect(), false);
    }

    EyeOfSingularityTriggeredAbility(final EyeOfSingularityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EyeOfSingularityTriggeredAbility copy() {
        return new EyeOfSingularityTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);

        if (event.getTargetId().equals(this.getSourceId())) {
            return false;
        }

        if (permanent != null && !permanent.isBasic()) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a permanent other than a basic land enters the battlefield, destroy all other permanents with that name. They can't be regenerated.";
    }
}

class EyeOfSingularityTriggeredEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
    }

    EyeOfSingularityTriggeredEffect() {
        super(Outcome.DestroyPermanent);
    }

    EyeOfSingularityTriggeredEffect(final EyeOfSingularityTriggeredEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        HashMap<UUID, Integer> toDestroy = new HashMap<>();
        Permanent etbPermanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));

        if (etbPermanent == null) {
            return false;
        }
        String cn = etbPermanent.getName();

        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            String cardName = permanent.getName();
            if (cardName.equals(cn) && !Objects.equals(permanent.getId(), etbPermanent.getId())) {
                toDestroy.put(permanent.getId(), 1);
            }
        }

        for (UUID id : toDestroy.keySet()) {
            Permanent permanent = game.getPermanent(id);
            if (permanent != null) {
                permanent.destroy(source.getSourceId(), game, false);
            }
        }

        return true;
    }

    @Override
    public EyeOfSingularityTriggeredEffect copy() {
        return new EyeOfSingularityTriggeredEffect(this);
    }
}
