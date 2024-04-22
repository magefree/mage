package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadowStinger extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.ROGUE, "another untapped Rogue you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TappedPredicate.UNTAPPED);
    }

    public ShadowStinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Tap another untapped Rogue you control: Shadow Stinger gains deathtouch until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ), new TapTargetCost(new TargetControlledPermanent(filter))));

        // Whenever Shadow Stinger deals combat damage to a player, that player mills three cards.
        this.addAbility(new ShadowStingerTriggeredAbility());
    }

    private ShadowStinger(final ShadowStinger card) {
        super(card);
    }

    @Override
    public ShadowStinger copy() {
        return new ShadowStinger(this);
    }
}

class ShadowStingerTriggeredAbility extends TriggeredAbilityImpl {

    ShadowStingerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MillCardsTargetEffect(3));
    }

    private ShadowStingerTriggeredAbility(final ShadowStingerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShadowStingerTriggeredAbility copy() {
        return new ShadowStingerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, that player mills three cards.";
    }
}
