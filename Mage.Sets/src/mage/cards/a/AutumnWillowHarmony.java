package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.ForestDryadToken;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class AutumnWillowHarmony extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("land creature");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    public AutumnWillowHarmony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // When Autumn Willow enters, create a 1/1 green Forest Dryad land creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ForestDryadToken())));

        // Whenever you tap a land creature for mana, add an additional {G}.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
            new BasicManaEffect(Mana.GreenMana(1)).setText("add an additional {G}"),
            filter, SetTargetPointer.NONE
        ).setTriggerPhrase("Whenever you tap a land creature for mana, "));
    }

    private AutumnWillowHarmony(final AutumnWillowHarmony card) {
        super(card);
    }

    @Override
    public AutumnWillowHarmony copy() {
        return new AutumnWillowHarmony(this);
    }
}
