package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class SardianCliffstomper extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.MOUNTAIN));
    private static final Hint hint = new ValueHint("Mountains you control", xValue);

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.MOUNTAIN);
    private static final PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(filter);
    private static final SardianCliffstomperCondition condition = new SardianCliffstomperCondition(filter);

    public SardianCliffstomper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // As long as it's your turn and you control four or more Mountains, Sardian Cliffstomper gets +X/+0, where X is the number of Mountains you control.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(count, StaticValue.get(0), Duration.WhileOnBattlefield),
                condition,
                "As long as it's your turn and you control four or more Mountains, {this} gets +X/+0, where X is the number of Mountains you control."
        )));
        this.getSpellAbility().addHint(hint);
    }

    private SardianCliffstomper(final SardianCliffstomper card) {
        super(card);
    }

    @Override
    public SardianCliffstomper copy() {
        return new SardianCliffstomper(this);
    }
}

class SardianCliffstomperCondition implements Condition {

    private final FilterPermanent filter;

    public SardianCliffstomperCondition(FilterPermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.isActivePlayer(source.getControllerId()) &&
                game.getBattlefield().count(filter, source.getControllerId(), source, game) >= 4;
    }
}
