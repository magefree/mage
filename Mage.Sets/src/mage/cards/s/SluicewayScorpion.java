
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ScavengeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class SluicewayScorpion extends CardImpl {

    public SluicewayScorpion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{G}");
        this.subtype.add(SubType.SCORPION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // Scavenge {1}{B}{G}
        this.addAbility(new ScavengeAbility(new ManaCostsImpl<>("{1}{B}{G}")));
    }

    private SluicewayScorpion(final SluicewayScorpion card) {
        super(card);
    }

    @Override
    public SluicewayScorpion copy() {
        return new SluicewayScorpion(this);
    }
}
