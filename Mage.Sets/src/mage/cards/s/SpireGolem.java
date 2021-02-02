
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.AffinityForLandTypeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SpireGolem extends CardImpl {

    public SpireGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Affinity for Islands
        this.addAbility(new AffinityForLandTypeAbility(SubType.ISLAND, "Islands"));
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
