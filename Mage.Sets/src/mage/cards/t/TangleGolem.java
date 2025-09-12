package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.AffinityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TangleGolem extends CardImpl {

    public TangleGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Affinity for Forests
        this.addAbility(new AffinityAbility(AffinityType.FORESTS));
    }

    private TangleGolem(final TangleGolem card) {
        super(card);
    }

    @Override
    public TangleGolem copy() {
        return new TangleGolem(this);
    }
}
