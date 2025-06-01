package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.AffinityAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SpireGolem extends CardImpl {

    public SpireGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Affinity for Islands
        this.addAbility(new AffinityAbility(AffinityType.ISLANDS));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private SpireGolem(final SpireGolem card) {
        super(card);
    }

    @Override
    public SpireGolem copy() {
        return new SpireGolem(this);
    }
}
