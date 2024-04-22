

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class Blistergrub extends CardImpl {

    public Blistergrub (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Swampwalk (This creature can't be blocked as long as defending player controls a Swamp.)
        this.addAbility(new SwampwalkAbility());
        // When Blistergrub dies, each opponent loses 2 life.
        this.addAbility(new DiesSourceTriggeredAbility(new LoseLifeOpponentsEffect(2), false));
    }

    private Blistergrub(final Blistergrub card) {
        super(card);
    }

    @Override
    public Blistergrub copy() {
        return new Blistergrub(this);
    }
}
