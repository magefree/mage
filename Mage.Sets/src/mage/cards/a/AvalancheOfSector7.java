package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvalancheOfSector7 extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("artifacts your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public AvalancheOfSector7(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Avalanche of Sector 7's power is equal to the number of artifacts your opponents control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(xValue)));

        // Whenever an opponent activates an ability of an artifact they control, Avalanche of Sector 7 deals 1 damage to that player.
        this.addAbility(new AvalancheOfSector7TriggeredAbility());
    }

    private AvalancheOfSector7(final AvalancheOfSector7 card) {
        super(card);
    }

    @Override
    public AvalancheOfSector7 copy() {
        return new AvalancheOfSector7(this);
    }
}

class AvalancheOfSector7TriggeredAbility extends TriggeredAbilityImpl {

    AvalancheOfSector7TriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1, true, "that player", true));
        setTriggerPhrase("Whenever an opponent activates an ability of an artifact they control, ");
    }

    private AvalancheOfSector7TriggeredAbility(final AvalancheOfSector7TriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AvalancheOfSector7TriggeredAbility copy() {
        return new AvalancheOfSector7TriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent == null || !permanent.isArtifact(game) || !permanent.isControlledBy(event.getPlayerId())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        return true;
    }
}
