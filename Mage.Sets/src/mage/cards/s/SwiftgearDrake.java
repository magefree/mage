package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwiftgearDrake extends CardImpl {

    public SwiftgearDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Swiftgear Drake enters the battlefield, put up to one target card from a graveyard on the bottom of its owner's library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(false));
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        this.addAbility(ability);
    }

    private SwiftgearDrake(final SwiftgearDrake card) {
        super(card);
    }

    @Override
    public SwiftgearDrake copy() {
        return new SwiftgearDrake(this);
    }
}
