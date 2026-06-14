package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.SourceDealsNoncombatDamageToOpponentTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class ThorGuardianOfMidgard extends CardImpl {

    public ThorGuardianOfMidgard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a source you control deals noncombat damage to an opponent, you may exile that many cards from the top of your library.
        // You may play those cards this turn.
        this.addAbility(new SourceDealsNoncombatDamageToOpponentTriggeredAbility(
            new ExileTopXMayPlayUntilEffect(SavedDamageValue.MANY, false, Duration.EndOfTurn)
                .setText("you may exile that many cards from the top of your library. You may play those cards this turn."),
            true,
            SetTargetPointer.NONE
        ));
    }

    private ThorGuardianOfMidgard(final ThorGuardianOfMidgard card) {
        super(card);
    }

    @Override
    public ThorGuardianOfMidgard copy() {
        return new ThorGuardianOfMidgard(this);
    }
}
