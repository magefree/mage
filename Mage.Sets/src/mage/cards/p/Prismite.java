package mage.cards.p;

import mage.MageInt;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Prismite extends CardImpl {

    public Prismite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new GenericManaCost(2)));
    }

    private Prismite(final Prismite card) {
        super(card);
    }

    @Override
    public Prismite copy() {
        return new Prismite(this);
    }
}
