
package mage.cards.b;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class Bloodletter extends CardImpl {

    public Bloodletter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When the names of three or more nonland permanents begin with the same letter, sacrifice Bloodletter. If you do, it deals 2 damage to each creature and each player.
        this.addAbility(new BloodletterStateTriggeredAbility());
    }

    private Bloodletter(final Bloodletter card) {
        super(card);
    }

    @Override
    public Bloodletter copy() {
        return new Bloodletter(this);
    }
}

class BloodletterStateTriggeredAbility extends StateTriggeredAbility {

    public BloodletterStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BloodletterEffect());
        setTriggerPhrase("When the names of three or more nonland permanents begin with the same letter, ");
    }

    public BloodletterStateTriggeredAbility(final BloodletterStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BloodletterStateTriggeredAbility copy() {
        return new BloodletterStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Map<Character, Integer> initialCount = new HashMap<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterNonlandPermanent(), getControllerId(), this, game)) {
            Character initial = permanent.getName().charAt(0);
            initialCount.putIfAbsent(initial, 0);
            initialCount.put(initial, initialCount.get(initial) + 1);
        }
        for (Map.Entry<Character, Integer> entry : initialCount.entrySet()) {
            if (entry.getValue() >= 3) {
                return true;
            }
        }
        return false;
    }
}

class BloodletterEffect extends OneShotEffect {

    public BloodletterEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice {this}. If you do, it deals 2 damage to each creature and each player";
    }

    public BloodletterEffect(final BloodletterEffect effect) {
        super(effect);
    }

    @Override
    public BloodletterEffect copy() {
        return new BloodletterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.sacrifice(source, game)) {
            return new DamageEverythingEffect(2).apply(game, source);
        }
        return false;
    }
}
