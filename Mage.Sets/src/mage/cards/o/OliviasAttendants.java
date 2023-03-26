package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.BloodToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OliviasAttendants extends CardImpl {

    public OliviasAttendants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Olivia's Attendants deals damage, create that many Blood tokens.
        this.addAbility(new OliviasAttendantsTriggeredAbility());

        // {2}{R}: Olivia's Attendants deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new ManaCostsImpl<>("{2}{R}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private OliviasAttendants(final OliviasAttendants card) {
        super(card);
    }

    @Override
    public OliviasAttendants copy() {
        return new OliviasAttendants(this);
    }
}

class OliviasAttendantsTriggeredAbility extends TriggeredAbilityImpl {

    OliviasAttendantsTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private OliviasAttendantsTriggeredAbility(final OliviasAttendantsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OliviasAttendantsTriggeredAbility copy() {
        return new OliviasAttendantsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER_BATCH
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int amount = ((DamagedBatchEvent) event)
                .getEvents()
                .stream()
                .filter(e -> e.getAttackerId().equals(this.getSourceId()))
                .mapToInt(GameEvent::getAmount)
                .sum();
        if (amount < 1) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new CreateTokenEffect(new BloodToken(), amount));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage, create that many Blood tokens.";
    }
}
