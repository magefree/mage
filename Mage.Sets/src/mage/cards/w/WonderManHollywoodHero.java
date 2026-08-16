package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PowerUpAbility;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

/**
 *
 * @author Grath
 */
public final class WonderManHollywoodHero extends CardImpl {

    public WonderManHollywoodHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PERFORMER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each power-up ability of permanents you control can be activated an additional time.
        this.addAbility(new SimpleStaticAbility(new WonderManHollywoodHeroEffect()));

        // Power-up -- {5}{R}{R}: Put two +1/+1 counters on Wonder Man.
        this.addAbility(new PowerUpAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                new ManaCostsImpl<>("{5}{R}{R}")
        ));
    }

    private WonderManHollywoodHero(final WonderManHollywoodHero card) {
        super(card);
    }

    @Override
    public WonderManHollywoodHero copy() {
        return new WonderManHollywoodHero(this);
    }
}

class WonderManHollywoodHeroEffect extends ReplacementEffectImpl {

    WonderManHollywoodHeroEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Each power-up ability of permanents you control can be activated an additional time.";
    }

    private WonderManHollywoodHeroEffect(final WonderManHollywoodHeroEffect effect) {
        super(effect);
    }

    @Override
    public WonderManHollywoodHeroEffect copy() {
        return new WonderManHollywoodHeroEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MAX_ACTIVATIONS && event.getFlag();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Ability ability = game.getAbility(event.getTargetId(), event.getSourceId()).orElse(null);
        if (!(ability instanceof PowerUpAbility)) {
            return false;
        }
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}