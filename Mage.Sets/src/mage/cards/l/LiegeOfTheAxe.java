
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class LiegeOfTheAxe extends CardImpl {

    public LiegeOfTheAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Morph {1}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{1}{W}")));
        // When Liege of the Axe is turned face up, untap it.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new UntapSourceEffect()));
    }

    private LiegeOfTheAxe(final LiegeOfTheAxe card) {
        super(card);
    }

    @Override
    public LiegeOfTheAxe copy() {
        return new LiegeOfTheAxe(this);
    }
}
