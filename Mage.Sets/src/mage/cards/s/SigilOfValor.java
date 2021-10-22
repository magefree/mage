package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import static mage.filter.predicate.permanent.ControllerControlsIslandPredicate.filter;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class SigilOfValor extends CardImpl {

    public SigilOfValor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks alone, it gets +1/+1 until end of turn for each other creature you control.
        this.addAbility(new SigilOfValorTriggeredAbility(new SigilOfValorCount()));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
    }

    private SigilOfValor(final SigilOfValor card) {
        super(card);
    }

    @Override
    public SigilOfValor copy() {
        return new SigilOfValor(this);
    }
}

class SigilOfValorTriggeredAbility extends TriggeredAbilityImpl {

    public SigilOfValorTriggeredAbility(DynamicValue boostValue) {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(boostValue, boostValue, Duration.EndOfTurn));
    }

    public SigilOfValorTriggeredAbility(final SigilOfValorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SigilOfValorTriggeredAbility copy() {
        return new SigilOfValorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.isActivePlayer(getControllerId())) {
            if (game.getCombat().attacksAlone()) {
                Permanent equipment = game.getPermanent(getSourceId());
                UUID attackerId = game.getCombat().getAttackers().get(0);
                if (equipment != null
                        && equipment.isAttachedTo(attackerId)) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(attackerId, game));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature attacks alone, it gets +1/+1 until end of turn for each other creature you control.";
    }

}

class SigilOfValorCount implements DynamicValue {

    public SigilOfValorCount() {
    }

    public SigilOfValorCount(final SigilOfValorCount dynamicValue) {
        super();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent equipment = game.getPermanent(sourceAbility.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            FilterPermanent filterPermanent = new FilterControlledCreaturePermanent();
            filterPermanent.add(Predicates.not(new CardIdPredicate(equipment.getAttachedTo())));
            return game.getBattlefield().count(filterPermanent, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new SigilOfValorCount(this);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return filter.getMessage();
    }
}
