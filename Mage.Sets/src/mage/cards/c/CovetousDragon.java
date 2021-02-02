
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public final class CovetousDragon extends CardImpl {

    public CovetousDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When you control no artifacts, sacrifice Covetous Dragon.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                new FilterArtifactPermanent("no artifacts"), ComparisonType.EQUAL_TO, 0,
                new SacrificeSourceEffect()));
    }

    private CovetousDragon(final CovetousDragon card) {
        super(card);
    }

    @Override
    public CovetousDragon copy() {
        return new CovetousDragon(this);
    }
}
