package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EquippedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZamrielSeraphOfSteel extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(EquippedPredicate.instance);
    }

    public ZamrielSeraphOfSteel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As long as it's your turn, equipped creatures you control have indestructible.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                        IndestructibleAbility.getInstance(),
                        Duration.WhileOnBattlefield, filter
                ), MyTurnCondition.instance, "as long as it's your turn, " +
                "equipped creatures you control have indestructible"
        )).addHint(MyTurnHint.instance));
    }

    private ZamrielSeraphOfSteel(final ZamrielSeraphOfSteel card) {
        super(card);
    }

    @Override
    public ZamrielSeraphOfSteel copy() {
        return new ZamrielSeraphOfSteel(this);
    }
}
