package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HajarLoyalBodyguard extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creatures");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public HajarLoyalBodyguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sacrifice Hajar, Loyal Bodyguard: Legendary creatures you control get +1/+0 and gain indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, filter
        ).setText("legendary creatures you control get +1/+0"), new SacrificeSourceCost());
        ability.addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("and gain indestructible until end of turn"));
        this.addAbility(ability);
    }

    private HajarLoyalBodyguard(final HajarLoyalBodyguard card) {
        super(card);
    }

    @Override
    public HajarLoyalBodyguard copy() {
        return new HajarLoyalBodyguard(this);
    }
}
