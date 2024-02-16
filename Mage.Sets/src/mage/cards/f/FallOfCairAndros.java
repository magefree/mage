package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FallOfCairAndros extends CardImpl {

    public FallOfCairAndros(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever a creature an opponent controls is dealt excess noncombat damage, amass Orcs X, where X is that excess damage.
        this.addAbility(new FallOfCairAndrosTriggeredAbility());

        // {7}{R}: Fall of Cair Andros deals 7 damage to target creature.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(7), new ManaCostsImpl("{7}{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FallOfCairAndros(final FallOfCairAndros card) {
        super(card);
    }

    @Override
    public FallOfCairAndros copy() {
        return new FallOfCairAndros(this);
    }
}

class FallOfCairAndrosTriggeredAbility extends TriggeredAbilityImpl {

    FallOfCairAndrosTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private FallOfCairAndrosTriggeredAbility(final FallOfCairAndrosTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FallOfCairAndrosTriggeredAbility copy() {
        return new FallOfCairAndrosTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !permanent.isCreature(game)
                || !game.getOpponents(getControllerId()).contains(permanent.getControllerId())) {
            return false;
        }
        DamagedPermanentEvent dEvent = (DamagedPermanentEvent) event;
        if (dEvent.isCombatDamage() || dEvent.getExcess() < 1) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new AmassEffect(dEvent.getExcess(), SubType.ORC));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls is dealt excess noncombat damage, " +
                "amass Orcs X, where X is that excess damage.";
    }
}
