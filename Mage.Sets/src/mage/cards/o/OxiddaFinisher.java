package mage.cards.o;

import mage.MageInt;
import mage.abilities.keyword.AffinityAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OxiddaFinisher extends CardImpl {

    public OxiddaFinisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Affinity for Equipment
        this.addAbility(new AffinityAbility(AffinityType.EQUIPMENT));

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private OxiddaFinisher(final OxiddaFinisher card) {
        super(card);
    }

    @Override
    public OxiddaFinisher copy() {
        return new OxiddaFinisher(this);
    }
}
