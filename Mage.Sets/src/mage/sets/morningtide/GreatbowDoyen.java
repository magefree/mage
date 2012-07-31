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
package mage.sets.morningtide;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class GreatbowDoyen extends CardImpl<GreatbowDoyen> {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("other Archer creatures");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new SubtypePredicate("Archer"));
    }

    public GreatbowDoyen(UUID ownerId) {
        super(ownerId, 125, "Greatbow Doyen", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.expansionSetCode = "MOR";
        this.subtype.add("Elf");
        this.subtype.add("Archer");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Other Archer creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Constants.Duration.WhileOnBattlefield, filter, true)));

        // Whenever an Archer you control deals damage to a creature, that Archer deals that much damage to that creature's controller.
        this.addAbility(new GreatbowDoyenTriggeredAbility());

    }

    public GreatbowDoyen(final GreatbowDoyen card) {
        super(card);
    }

    @Override
    public GreatbowDoyen copy() {
        return new GreatbowDoyen(this);
    }
}

class GreatbowDoyenTriggeredAbility extends TriggeredAbilityImpl<GreatbowDoyenTriggeredAbility> {

    public GreatbowDoyenTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new GreatbowDoyenEffect());
    }

    public GreatbowDoyenTriggeredAbility(final GreatbowDoyenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GreatbowDoyenTriggeredAbility copy() {
        return new GreatbowDoyenTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE) {
            Permanent creature = game.getPermanent(event.getSourceId());
            Permanent damagedCreature = game.getPermanent(event.getTargetId());
            if (creature.getCardType().contains(CardType.CREATURE) && damagedCreature != null
                    && creature.getSubtype().contains("Archer")
                    && creature.getControllerId().equals(controllerId) && creature != null) {
                this.getEffects().get(0).setValue("damageAmount", event.getAmount());
                this.getEffects().get(0).setValue("controller", damagedCreature.getControllerId());
                this.getEffects().get(0).setValue("source", event.getSourceId());
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an Archer you control deals damage to a creature, " + super.getRule();
    }
}

class GreatbowDoyenEffect extends OneShotEffect<GreatbowDoyenEffect> {

    public GreatbowDoyenEffect() {
        super(Constants.Outcome.Damage);
        this.staticText = "it deals that much damage to its controller";
    }

    public GreatbowDoyenEffect(final GreatbowDoyenEffect effect) {
        super(effect);
    }

    @Override
    public GreatbowDoyenEffect copy() {
        return new GreatbowDoyenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damageAmount = (Integer) this.getValue("damageAmount");
        UUID controllerId = (UUID) this.getValue("controller");
        UUID sourceOfDamage = (UUID) this.getValue("source");
        if (damageAmount != null && controllerId != null) {
            Permanent permanent = game.getPermanent(sourceOfDamage);
            if (permanent == null) {
                permanent = (Permanent) game.getLastKnownInformation(sourceOfDamage, Constants.Zone.BATTLEFIELD);
            }
            if (permanent != null) {
                Player player = game.getPlayer(controllerId);
                if (player != null) {
                    player.damage(damageAmount, sourceOfDamage, game, false, true);
                    return true;
                }
            }
        }
        return false;
    }
}
