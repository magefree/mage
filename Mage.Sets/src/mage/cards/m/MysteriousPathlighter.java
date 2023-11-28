package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysteriousPathlighter extends CardImpl {

    public MysteriousPathlighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each creature you control that has an Adventure enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new MysteriousPathlighterEffect()));
    }

    private MysteriousPathlighter(final MysteriousPathlighter card) {
        super(card);
    }

    @Override
    public MysteriousPathlighter copy() {
        return new MysteriousPathlighter(this);
    }
}

class MysteriousPathlighterEffect extends ReplacementEffectImpl {

    MysteriousPathlighterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        this.staticText = "Each creature you control that has an Adventure " +
                "enters the battlefield with an additional +1/+1 counter on it";
    }

    private MysteriousPathlighterEffect(MysteriousPathlighterEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent instanceof PermanentCard
                && ((PermanentCard) permanent).getCard() instanceof AdventureCard
                && permanent.isControlledBy(source.getControllerId())
                && permanent.isCreature(game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public MysteriousPathlighterEffect copy() {
        return new MysteriousPathlighterEffect(this);
    }
}
