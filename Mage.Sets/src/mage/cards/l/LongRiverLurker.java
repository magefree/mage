package mage.cards.l;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamagedBatchBySourceEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class LongRiverLurker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.FROG, "Frogs");

    public LongRiverLurker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}"), false));

        // Other Frogs you control have ward {1}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(1)), Duration.WhileOnBattlefield, filter, true)));

        // When Long River Lurker enters, target creature you control can't be blocked this turn. Whenever that creature deals combat damage this turn, you may exile it. If you do, return it to the battlefield under its owner's control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CantBeBlockedTargetEffect());
        ability.addEffect(new LongRiverLurkerEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private LongRiverLurker(final LongRiverLurker card) {
        super(card);
    }

    @Override
    public LongRiverLurker copy() {
        return new LongRiverLurker(this);
    }
}

class LongRiverLurkerEffect extends OneShotEffect {

    LongRiverLurkerEffect() {
        super(Outcome.Benefit);
        staticText = "Whenever that creature deals combat damage this turn, you may exile it. If you do, return it to the battlefield under its owner's control.";
    }

    private LongRiverLurkerEffect(final LongRiverLurkerEffect effect) {
        super(effect);
    }

    @Override
    public LongRiverLurkerEffect copy() {
        return new LongRiverLurkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        game.addDelayedTriggeredAbility(new LongRiverLurkerTriggeredAbility(new MageObjectReference(permanent, game)), source);
        return true;
    }
}

class LongRiverLurkerTriggeredAbility extends DelayedTriggeredAbility implements BatchTriggeredAbility<DamagedEvent> {

    private final MageObjectReference mor;

    LongRiverLurkerTriggeredAbility(MageObjectReference mor) {
        super(new ExileThenReturnTargetEffect(false, false).setTargetPointer(new FixedTarget(mor)),
                Duration.EndOfTurn, false, true);
        this.mor = mor;
    }

    private LongRiverLurkerTriggeredAbility(final LongRiverLurkerTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
    }

    @Override
    public LongRiverLurkerTriggeredAbility copy() {
        return new LongRiverLurkerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_BY_SOURCE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return ((DamagedBatchBySourceEvent) event).isCombatDamage()
                && mor.refersTo(event.getSourceId(), game);
    }

    @Override
    public String getRule() {
        return "Whenever that creature deals combat damage this turn, you may exile it. If you do, return it to the battlefield under its owner's control.";
    }
}
