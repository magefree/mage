package mage.cards.o;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrnithopterOfParadise extends CardImpl {

    public OrnithopterOfParadise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private OrnithopterOfParadise(final OrnithopterOfParadise card) {
        super(card);
    }

    @Override
    public OrnithopterOfParadise copy() {
        return new OrnithopterOfParadise(this);
    }
}
