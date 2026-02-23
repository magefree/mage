package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ManaSpentToCastCount;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TophGreatestEarthbender extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("land creatures");

    static {
        filter.add(CardType.LAND.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
    }

    public TophGreatestEarthbender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Toph enters, earthbend X, where X is the amount of mana spent to cast her.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EarthbendTargetEffect(ManaSpentToCastCount.instance, true));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);

        // Land creatures you control have double strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private TophGreatestEarthbender(final TophGreatestEarthbender card) {
        super(card);
    }

    @Override
    public TophGreatestEarthbender copy() {
        return new TophGreatestEarthbender(this);
    }
}
