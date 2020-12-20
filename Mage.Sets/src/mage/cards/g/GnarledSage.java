package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.watchers.common.CardsDrawnThisTurnWatcher;

import java.util.UUID;

/**
 * @author arcox
 */
public final class GnarledSage extends CardImpl {

    public GnarledSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(ReachAbility.getInstance());

        // As long as you’ve drawn two or more cards this turn, Gnarled Sage gets +0/+2 and has vigilance.
        Ability ability = new SimpleStaticAbility(
                new ConditionalContinuousEffect(
                        new BoostSourceEffect(0, 2, Duration.WhileOnBattlefield),
                        GnarledSageCondition.instance,
                        "As long as you've drawn two or more cards this turn, {this} gets +0/+2"
                ));

        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield),
                GnarledSageCondition.instance,
                "and has vigilance"
        ));

        this.addAbility(ability);
    }

    private GnarledSage(final GnarledSage card) {
        super(card);
    }

    @Override
    public GnarledSage copy() {
        return new GnarledSage(this);
    }
}

enum GnarledSageCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CardsDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsDrawnThisTurnWatcher.class);
        return watcher != null && watcher.getCardsDrawnThisTurn(source.getControllerId()) > 1;
    }
}