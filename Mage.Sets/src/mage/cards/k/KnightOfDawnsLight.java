package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author weirddan455
 */
public final class KnightOfDawnsLight extends CardImpl {

    public KnightOfDawnsLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // If you would gain life, you gain that much life plus 1 instead.
        this.addAbility(new SimpleStaticAbility(new KnightOfDawnsLightEffect()));

        // {1}{W}: Knight of Dawn's Light gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{W}")
        ));
    }

    private KnightOfDawnsLight(final KnightOfDawnsLight card) {
        super(card);
    }

    @Override
    public KnightOfDawnsLight copy() {
        return new KnightOfDawnsLight(this);
    }
}

class KnightOfDawnsLightEffect extends ReplacementEffectImpl {

    public KnightOfDawnsLightEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If you would gain life, you gain that much life plus 1 instead.";
    }

    private KnightOfDawnsLightEffect(final KnightOfDawnsLightEffect effect) {
        super(effect);
    }

    @Override
    public KnightOfDawnsLightEffect copy() {
        return new KnightOfDawnsLightEffect(this);
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
