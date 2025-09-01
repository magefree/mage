package mage.cards.o;

import mage.MageInt;
import mage.abilities.keyword.AffinityAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OxiddaGolem extends CardImpl {

    public OxiddaGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Affinity for Mountains
        this.addAbility(new AffinityAbility(AffinityType.MOUNTAINS));

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private OxiddaGolem(final OxiddaGolem card) {
        super(card);
    }

    @Override
    public OxiddaGolem copy() {
        return new OxiddaGolem(this);
    }
}
