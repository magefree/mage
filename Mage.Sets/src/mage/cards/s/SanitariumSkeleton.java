
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class SanitariumSkeleton extends CardImpl {

    public SanitariumSkeleton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {2}{B}: Return Sanitarium Skeleton from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{2}{B}")));
    }

    private SanitariumSkeleton(final SanitariumSkeleton card) {
        super(card);
    }

    @Override
    public SanitariumSkeleton copy() {
        return new SanitariumSkeleton(this);
    }
}
