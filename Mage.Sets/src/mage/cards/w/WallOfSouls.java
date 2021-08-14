
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class WallOfSouls extends CardImpl {

    public WallOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever Wall of Souls is dealt combat damage, it deals that much damage to target opponent.
        Ability ability = new WallOfSoulsTriggeredAbility();
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    private WallOfSouls(final WallOfSouls card) {
        super(card);
    }

    @Override
    public WallOfSouls copy() {
        return new WallOfSouls(this);
    }
}

class WallOfSoulsTriggeredAbility extends TriggeredAbilityImpl {

    public WallOfSoulsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WallOfSoulsDealDamageEffect());
    }

    public WallOfSoulsTriggeredAbility(final WallOfSoulsTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public WallOfSoulsTriggeredAbility copy() {
        return new WallOfSoulsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.sourceId) && ((DamagedEvent) event).isCombatDamage()) {
            this.getEffects().get(0).setValue("damage", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} is dealt combat damage, " ;
    }
}

class WallOfSoulsDealDamageEffect extends OneShotEffect {

    public WallOfSoulsDealDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals that much damage to target opponent or planeswalker";
    }

    public WallOfSoulsDealDamageEffect(final WallOfSoulsDealDamageEffect effect) {
        super(effect);
    }

    @Override
    public WallOfSoulsDealDamageEffect copy() {
        return new WallOfSoulsDealDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            return game.damagePlayerOrPlaneswalker(source.getFirstTarget(), amount, source.getSourceId(), source, game, false, true) > 0;
        }
        return false;
    }
}
