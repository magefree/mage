package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
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
public final class AangDestinedSavior extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("land creatures");

    static {
        filter.add(CardType.LAND.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
    }

    public AangDestinedSavior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Land creatures you control have vigilance.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // At the beginning of combat on your turn, earthbend 2.
        Ability ability = new BeginningOfCombatTriggeredAbility(new EarthbendTargetEffect(2));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);
    }

    private AangDestinedSavior(final AangDestinedSavior card) {
        super(card);
    }

    @Override
    public AangDestinedSavior copy() {
        return new AangDestinedSavior(this);
    }
}
