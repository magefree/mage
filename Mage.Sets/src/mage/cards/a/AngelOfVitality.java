package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AngelOfVitality extends CardImpl {

    public AngelOfVitality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If you would gain life, you gain that much life plus 1 instead.
        this.addAbility(new SimpleStaticAbility(new AngelOfVitalityEffect()));

        // Angel of Vitality gets +2/+2 as long as you have 25 or more life.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                AngelOfVitalityCondition.instance, "{this} gets +2/+2 as long as you have 25 or more life"
        )));
    }

    private AngelOfVitality(final AngelOfVitality card) {
        super(card);
    }

    @Override
    public AngelOfVitality copy() {
        return new AngelOfVitality(this);
    }
}

class AngelOfVitalityEffect extends ReplacementEffectImpl {

    AngelOfVitalityEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would gain life, you gain that much life plus one instead";
    }

    private AngelOfVitalityEffect(final AngelOfVitalityEffect effect) {
        super(effect);
    }

    @Override
    public AngelOfVitalityEffect copy() {
        return new AngelOfVitalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAIN_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId()) && (source.getControllerId() != null);
    }
}

enum AngelOfVitalityCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getLife() >= 25;
    }
}
