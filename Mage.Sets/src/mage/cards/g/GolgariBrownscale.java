
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DredgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class GolgariBrownscale extends CardImpl {

    public GolgariBrownscale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Golgari Brownscale is put into your hand from your graveyard, you gain 2 life.
        this.addAbility(new ZoneChangeTriggeredAbility(Zone.ALL, Zone.GRAVEYARD, Zone.HAND, new GainLifeEffect(2), 
                "When {this} is put into your hand from your graveyard, ", false));
        
        // Dredge 2
        this.addAbility(new DredgeAbility(2));
    }

    private GolgariBrownscale(final GolgariBrownscale card) {
        super(card);
    }

    @Override
    public GolgariBrownscale copy() {
        return new GolgariBrownscale(this);
    }
}
