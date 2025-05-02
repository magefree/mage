package mage.cards.s;

import mage.MageInt;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShepherdOfTheFlock extends AdventureCard {

    public ShepherdOfTheFlock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{1}{W}", "Usher to Safety", "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Usher to Safety
        // Return target permanent you control to its owner’s hand.
        this.getSpellCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetControlledPermanent());

        this.finalizeAdventure();
    }

    private ShepherdOfTheFlock(final ShepherdOfTheFlock card) {
        super(card);
    }

    @Override
    public ShepherdOfTheFlock copy() {
        return new ShepherdOfTheFlock(this);
    }
}
