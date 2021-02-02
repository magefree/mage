
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EvolveAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class CloudfinRaptor extends CardImpl {

    public CloudfinRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Evolve (Whenever a creature enters the battlefield under your control, if that creature
        // has greater power or toughness than this creature, put a +1/+1 counter on this creature.)
        this.addAbility(new EvolveAbility());
    }

    private CloudfinRaptor(final CloudfinRaptor card) {
        super(card);
    }

    @Override
    public CloudfinRaptor copy() {
        return new CloudfinRaptor(this);
    }
}
