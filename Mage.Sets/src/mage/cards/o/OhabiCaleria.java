package mage.cards.o;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.UntapAllDuringEachOtherPlayersUntapStepEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class OhabiCaleria extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Archers you control");

    static {
        filter.add(SubType.ARCHER.getPredicate());
    }

    public OhabiCaleria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Untap all Archers you control during each other player's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UntapAllDuringEachOtherPlayersUntapStepEffect(filter)));

        // Whenever an Archer you control deals damage to a creature, you may pay {2}. If you do, draw a card.
        this.addAbility(new OhabiCaleriaTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(2)
        )));
    }

    private OhabiCaleria(final OhabiCaleria card) {
        super(card);
    }

    @Override
    public OhabiCaleria copy() {
        return new OhabiCaleria(this);
    }
}

class OhabiCaleriaTriggeredAbility extends TriggeredAbilityImpl {

    public OhabiCaleriaTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever an Archer you control deals damage to a creature, ");
    }

    public OhabiCaleriaTriggeredAbility(final OhabiCaleriaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OhabiCaleriaTriggeredAbility copy() {
        return new OhabiCaleriaTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent creature = game.getPermanent(event.getSourceId());
        Permanent damagedCreature = game.getPermanent(event.getTargetId());
        if (creature != null && damagedCreature != null
                && creature.isCreature(game)
                && creature.hasSubtype(SubType.ARCHER, game)
                && creature.isControlledBy(controllerId)) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            this.getEffects().get(0).setValue("controller", damagedCreature.getControllerId());
            this.getEffects().get(0).setValue("source", event.getSourceId());
            return true;
        }
        return false;
    }
}
