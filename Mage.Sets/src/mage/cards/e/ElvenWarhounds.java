
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class ElvenWarhounds extends CardImpl {

    public ElvenWarhounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Elven Warhounds becomes blocked by a creature, put that creature on top of its owner's library.
        Effect effect = new PutOnLibraryTargetEffect(true);
        effect.setText("put that creature on top of its owner's library");
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(effect,  false));
    }

    private ElvenWarhounds(final ElvenWarhounds card) {
        super(card);
    }

    @Override
    public ElvenWarhounds copy() {
        return new ElvenWarhounds(this);
    }
}
