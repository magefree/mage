
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.AffinityForLandTypeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class OxiddaGolem extends CardImpl {

    public OxiddaGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Affinity for Mountains
        this.addAbility(new AffinityForLandTypeAbility(SubType.MOUNTAIN, "Mountains"));
        
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
