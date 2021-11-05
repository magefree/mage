package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author weirddan455
 */
public final class HeronOfHope extends CardImpl {

    public HeronOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If you would gain life, you gain that much life plus 1 instead.
        this.addAbility(new SimpleStaticAbility(new HeronOfHopeEffect()));

        // {1}{W}: Heron of Hope gains lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{W}")
        ));
    }

    private HeronOfHope(final HeronOfHope card) {
        super(card);
    }

    @Override
    public HeronOfHope copy() {
        return new HeronOfHope(this);
    }
}

class HeronOfHopeEffect extends ReplacementEffectImpl {

    public HeronOfHopeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would gain life, you gain that much life plus 1 instead";
    }

    private HeronOfHopeEffect(final HeronOfHopeEffect effect) {
        super(effect);
    }

    @Override
    public HeronOfHopeEffect copy() {
        return new HeronOfHopeEffect(this);
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
        return source.isControlledBy(event.getPlayerId());
    }
}
