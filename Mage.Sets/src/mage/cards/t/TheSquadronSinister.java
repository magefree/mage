package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MayhemAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class TheSquadronSinister extends CardImpl {

    // The +2/+2 effect wants a FilterCreaturePermanent
    private static final FilterCreaturePermanent filterCreature =
            new FilterCreaturePermanent(SubType.VILLAIN, "Other Villains you control");
    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent(SubType.VILLAIN, "Other Villains you control");

    public TheSquadronSinister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Other Villains you control get +2/+2 and have flying and haste.
        this.addAbility(new SimpleStaticAbility(
            new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filterCreature, true)));
        this.addAbility(new SimpleStaticAbility(
            new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)
                .setText("and have flying")
        ));
        this.addAbility(new SimpleStaticAbility(
            new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)
                .setText("and haste")
        ));

        // Mayhem {3}{U}{R}
        this.addAbility(new MayhemAbility(this, "{3}{U}{R}"));
    }

    private TheSquadronSinister(final TheSquadronSinister card) {
        super(card);
    }

    @Override
    public TheSquadronSinister copy() {
        return new TheSquadronSinister(this);
    }
}
