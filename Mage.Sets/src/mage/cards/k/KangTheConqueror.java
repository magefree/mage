package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author muz
 */
public final class KangTheConqueror extends CardImpl {

    public KangTheConqueror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Power-up -- {5}{U}{U}{U}: Put a +1/+1 counter on Kang. Take an extra turn after this one. During that turn, power-up abilities can't be activated.
        Ability ability = new PowerUpAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            new ManaCostsImpl<>("{5}{U}{U}{U}")
        );
        ability.addEffect(new AddExtraTurnControllerEffect(false, KangTheConquerorApplier.instance)
            .setText("take an extra turn after this one. During that turn, power-up abilities can't be activated"));
        this.addAbility(ability);
    }

    private KangTheConqueror(final KangTheConqueror card) {
        super(card);
    }

    @Override
    public KangTheConqueror copy() {
        return new KangTheConqueror(this);
    }
}

enum KangTheConquerorApplier implements AddExtraTurnControllerEffect.TurnModApplier {
    instance;

    @Override
    public void apply(UUID turnId, Ability source, Game game) {
        game.addEffect(new KangTheConquerorEffect(turnId), source);
    }
}

class KangTheConquerorEffect extends ContinuousRuleModifyingEffectImpl {

    private final UUID turnId;

    KangTheConquerorEffect(UUID turnId) {
        super(Duration.Custom, Outcome.Detriment);
        this.turnId = turnId;
    }

    private KangTheConquerorEffect(final KangTheConquerorEffect effect) {
        super(effect);
        this.turnId = effect.turnId;
    }

    @Override
    public KangTheConquerorEffect copy() {
        return new KangTheConquerorEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        return "Power-up abilities can't be activated during this turn.";
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (turnId == null || !turnId.equals(game.getState().getExtraTurnId())) {
            return false;
        }
        Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
        return ability.isPresent() && ability.get() instanceof PowerUpAbility;
    }
}
