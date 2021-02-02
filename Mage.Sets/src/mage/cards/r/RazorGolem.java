
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.AffinityForLandTypeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class RazorGolem extends CardImpl {

    public RazorGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Affinity for Plains
        this.addAbility(new AffinityForLandTypeAbility(SubType.PLAINS, "Plains"));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private RazorGolem(final RazorGolem card) {
        super(card);
    }

    @Override
    public RazorGolem copy() {
        return new RazorGolem(this);
    }
}
