package mage.cards.h;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedPlayerBatchEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HordewingSkaab extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.ZOMBIE, "Zombies");

    public HordewingSkaab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Other Zombies you control have flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever one or more Zombies you control deal combat damage to one or more of your opponents, you may draw cards equal to the number of opponents dealt damage this way. If you do, discard that many cards.
        this.addAbility(new HordewingSkaabTriggeredAbility());
    }

    private HordewingSkaab(final HordewingSkaab card) {
        super(card);
    }

    @Override
    public HordewingSkaab copy() {
        return new HordewingSkaab(this);
    }
}

class HordewingSkaabTriggeredAbility extends TriggeredAbilityImpl {

    HordewingSkaabTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, true);
    }

    private HordewingSkaabTriggeredAbility(final HordewingSkaabTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerBatchEvent dEvent = (DamagedPlayerBatchEvent) event;
        Set<UUID> opponents = new HashSet<>();
        for (DamagedEvent damagedEvent : dEvent.getEvents()) {
            if (!damagedEvent.isCombatDamage()) {
                continue;
            }
            Permanent permanent = game.getPermanent(damagedEvent.getSourceId());
            if (permanent == null
                    || !permanent.isControlledBy(getControllerId())
                    || !permanent.hasSubtype(SubType.ZOMBIE, game)
                    || !game.getOpponents(getControllerId()).contains(damagedEvent.getTargetId())) {
                continue;
            }
            opponents.add(damagedEvent.getTargetId());
        }
        if (opponents.size() < 1) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new DrawDiscardControllerEffect(opponents.size(), opponents.size()));
        return true;
    }

    @Override
    public HordewingSkaabTriggeredAbility copy() {
        return new HordewingSkaabTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more Zombies you control deal combat damage to one " +
                "or more of your opponents, you may draw cards equal to the number " +
                "of opponents dealt damage this way. If you do, discard that many cards.";
    }
}
