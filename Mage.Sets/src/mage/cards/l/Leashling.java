package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PutCardFromHandOnTopOfLibraryCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;


/**
 *
 * @author Quercitron
 */
public final class Leashling extends CardImpl {

    public Leashling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Put a card from your hand on top of your library: Return Leashling to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(new ReturnToHandSourceEffect(), new PutCardFromHandOnTopOfLibraryCost()));
    }

    private Leashling(final Leashling card) {
        super(card);
    }

    @Override
    public Leashling copy() {
        return new Leashling(this);
    }
}
