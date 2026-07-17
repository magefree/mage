package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.keyword.BlightControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.Counters;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadowUrchin extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with one or more counters on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public ShadowUrchin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/R}");

        this.subtype.add(SubType.OUPHE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever this creature attacks, blight 1.
        this.addAbility(new AttacksTriggeredAbility(new BlightControllerEffect(1)));

        // Whenever a creature you control with one or more counters on it dies, exile that many cards from the top of your library. Until your next end step, you may play those cards.
        this.addAbility(new DiesCreatureTriggeredAbility(new ExileTopXMayPlayUntilEffect(
                ShadowUrchinValue.instance, false, Duration.UntilYourNextEndStep
        ).setText("exile that many cards from the top of your library. " +
                "Until your next end step, you may play those cards"), false, filter));
    }

    private ShadowUrchin(final ShadowUrchin card) {
        super(card);
    }

    @Override
    public ShadowUrchin copy() {
        return new ShadowUrchin(this);
    }
}

enum ShadowUrchinValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Permanent) effect.getValue("creatureDied"))
                .map(permanent -> permanent.getCounters(game))
                .map(Counters::getTotalCount)
                .orElse(0);
    }

    @Override
    public ShadowUrchinValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
