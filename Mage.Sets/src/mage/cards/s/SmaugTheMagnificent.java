package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author muz
 */
public final class SmaugTheMagnificent extends CardImpl {

    private static final PermanentsOnBattlefieldCount xValue = new PermanentsOnBattlefieldCount(
        new FilterControlledPermanent(SubType.TREASURE, "Treasures you control")
    );
    private static final ValueHint hint = new ValueHint("Treasures you control", xValue);

    public SmaugTheMagnificent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Smaug attacks, he deals damage equal to the number of Treasures you control to any target.
        Ability ability = new AttacksTriggeredAbility(new DamageTargetEffect(xValue)
            .setText("he deals damage equal to the number of Treasures you control to any target"), false);
        ability.addTarget(new TargetAnyTarget());
        ability.addHint(hint);
        this.addAbility(ability);

        // At the beginning of your upkeep, create a Treasure token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new TreasureToken())));
    }

    private SmaugTheMagnificent(final SmaugTheMagnificent card) {
        super(card);
    }

    @Override
    public SmaugTheMagnificent copy() {
        return new SmaugTheMagnificent(this);
    }
}
