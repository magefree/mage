package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EarthbendingStudent extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("land creatures");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    public EarthbendingStudent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When this creature enters, earthbend 2.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EarthbendTargetEffect(2));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);

        // Land creatures you control have vigilance.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private EarthbendingStudent(final EarthbendingStudent card) {
        super(card);
    }

    @Override
    public EarthbendingStudent copy() {
        return new EarthbendingStudent(this);
    }
}
