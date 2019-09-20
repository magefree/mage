package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SignpostScarecrow extends CardImpl {

    public SignpostScarecrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {2}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new GenericManaCost(2)));
    }

    private SignpostScarecrow(final SignpostScarecrow card) {
        super(card);
    }

    @Override
    public SignpostScarecrow copy() {
        return new SignpostScarecrow(this);
    }
}
