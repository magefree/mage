package mage.cards.l;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LuciusTheEternal extends CardImpl {

    public LuciusTheEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Armor of Shrieking Souls -- When Lucius the Eternal dies, exile it and choose target creature an opponent controls. When that creature leaves the battlefield, return Lucius the Eternal from exile to the battlefield under its owner's control.
        Ability ability = new DiesSourceTriggeredAbility(new LuciusTheEternalEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Armor of Shrieking Souls"));
    }

    private LuciusTheEternal(final LuciusTheEternal card) {
        super(card);
    }

    @Override
    public LuciusTheEternal copy() {
        return new LuciusTheEternal(this);
    }
}

class LuciusTheEternalEffect extends OneShotEffect {

    LuciusTheEternalEffect() {
        super(Outcome.Detriment);
        staticText = "exile it and choose target creature an opponent controls. "
                + "When that creature leaves the battlefield, "
                + "return this card from exile to the battlefield under its owner's control.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }

        // exile Lucius (note: it may fail, we still have to create a delayed trigger)
        boolean exiled = new ExileSourceEffect(true).apply(game, source);
        game.processAction();

        new CreateDelayedTriggeredAbilityEffect(
                new LuciusTheEternalDelayedTriggeredAbility(
                        new ReturnFromExileEffect(Zone.BATTLEFIELD)
                )
        ).setTargetPointer(getTargetPointer().copy()).apply(game, source);

        // For better feedback, add an info to the targetted permanent.
        String luciusDescription = exiled ? CardUtil.getSourceIdName(game, source) : CardUtil.getSourceName(game, source);
        InfoEffect.addInfoToPermanent(game, source, permanent, "(When this creature leaves the battlefield, "
                + "return " + luciusDescription + " from exile to the battlefield under its owner's control)");

        return true;
    }

    private LuciusTheEternalEffect(final LuciusTheEternalEffect effect) {
        super(effect);
    }

    @Override
    public LuciusTheEternalEffect copy() {
        return new LuciusTheEternalEffect(this);
    }
}

class LuciusTheEternalDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private MageObjectReference mor;

    LuciusTheEternalDelayedTriggeredAbility(Effect effect) {
        super(effect, Duration.WhileOnBattlefield, true);
        setTriggerPhrase("When that creature leaves the battlefield, ");
        setLeavesTheBattlefieldTrigger(true);
    }

    protected LuciusTheEternalDelayedTriggeredAbility(final LuciusTheEternalDelayedTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
    }

    @Override
    public LuciusTheEternalDelayedTriggeredAbility copy() {
        return new LuciusTheEternalDelayedTriggeredAbility(this);
    }

    @Override
    public void init(Game game) {
        mor = new MageObjectReference(getFirstTarget(), game);
        getTargets().clear();
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent permanent = zEvent.getTarget();
        return Zone.BATTLEFIELD.equals(zEvent.getFromZone())
                && mor != null
                && mor.refersTo(permanent, game);
    }
}
