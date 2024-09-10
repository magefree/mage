package mage.cards.z;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZagrasThiefOfHeartbeats extends CardImpl {

    public ZagrasThiefOfHeartbeats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // This spell costs {1} less to cast for each creature in your party.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, PartyCount.instance)
        ).addHint(PartyCountHint.instance));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Other creatures you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        )));

        // Whenever a creature you control deals combat damage to a planeswalker, destroy that planeswalker.
        this.addAbility(new ZagrasThiefOfHeartbeatsTriggeredAbility());
    }

    private ZagrasThiefOfHeartbeats(final ZagrasThiefOfHeartbeats card) {
        super(card);
    }

    @Override
    public ZagrasThiefOfHeartbeats copy() {
        return new ZagrasThiefOfHeartbeats(this);
    }
}

class ZagrasThiefOfHeartbeatsTriggeredAbility extends TriggeredAbilityImpl {

    ZagrasThiefOfHeartbeatsTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private ZagrasThiefOfHeartbeatsTriggeredAbility(final ZagrasThiefOfHeartbeatsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ZagrasThiefOfHeartbeatsTriggeredAbility copy() {
        return new ZagrasThiefOfHeartbeatsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent == null
                || !permanent.isCreature(game)
                || !permanent.isControlledBy(getControllerId())) {
            return false;
        }
        Permanent damaged = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (damaged == null || !permanent.isPlaneswalker(game)) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new DestroyTargetEffect().setTargetPointer(new FixedTarget(event.getTargetId(), game)));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control deals combat damage to a planeswalker, destroy that planeswalker.";
    }
}
