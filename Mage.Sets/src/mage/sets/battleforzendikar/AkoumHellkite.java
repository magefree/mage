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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class AkoumHellkite extends CardImpl {

    public AkoumHellkite(UUID ownerId) {
        super(ownerId, 139, "Akoum Hellkite", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Dragon");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // <i>Landfall</i>-Whenever a land enters the battlefield under you control, Akoum Hellkite deals 1 damage to target creature or player.
        // If that land is a Mountain, Akoum Hellkite deals 2 damage to that creature or player instead.
        Ability ability = new AkoumHellkiteTriggeredAbility();
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public AkoumHellkite(final AkoumHellkite card) {
        super(card);
    }

    @Override
    public AkoumHellkite copy() {
        return new AkoumHellkite(this);
    }
}
class AkoumHellkiteTriggeredAbility extends TriggeredAbilityImpl {

    private static final String text = "<i>Landfall</i> - Whenever a land enters the battlefield under your control, {this} deals 1 damage to target creature or player. "
            + "If that land is a Mountain, Akoum Hellkite deals 2 damage to that creature or player instead.";

    public AkoumHellkiteTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AkoumHellkiteDamageEffect());
    }

    public AkoumHellkiteTriggeredAbility(final AkoumHellkiteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AkoumHellkiteTriggeredAbility copy() {
        return new AkoumHellkiteTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && permanent.getCardType().contains(CardType.LAND)
                && permanent.getControllerId().equals(getControllerId())) {
            Permanent sourcePermanent = game.getPermanent(getSourceId());
            if (sourcePermanent != null)
                for (Effect effect : getEffects()) {
                if (effect instanceof AkoumHellkiteDamageEffect) {
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return text;
    }
}

class AkoumHellkiteDamageEffect extends OneShotEffect {

    public AkoumHellkiteDamageEffect() {
        super(Outcome.Damage);
    }

    public AkoumHellkiteDamageEffect(final AkoumHellkiteDamageEffect effect) {
        super(effect);
    }

    @Override
    public AkoumHellkiteDamageEffect copy() {
        return new AkoumHellkiteDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        Player player = game.getPlayer(source.getFirstTarget());
        if (land != null && player != null) {
            if (land.hasSubtype("Mountain")) {
                player.damage(2, source.getSourceId(), game, false, true);
            } else {
                player.damage(1, source.getSourceId(), game, false, true);
            }
            return true;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (land != null && permanent != null) {
            if (land.hasSubtype("Mountain")) {
                permanent.damage(2, source.getSourceId(), game, false, true);
            } else {
                permanent.damage(1, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
