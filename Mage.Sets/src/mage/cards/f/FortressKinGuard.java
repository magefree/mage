package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.EndureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FortressKinGuard extends CardImpl {

    public FortressKinGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When this creature enters, it endures 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EndureSourceEffect(1)));
    }

    private FortressKinGuard(final FortressKinGuard card) {
        super(card);
    }

    @Override
    public FortressKinGuard copy() {
        return new FortressKinGuard(this);
    }
}
