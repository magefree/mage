package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author htrajan
 */
public final class GarrukSavageHerald extends CardImpl {

    public GarrukSavageHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}{G}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));

        // +1: Reveal the top card of your library. If it's a creature card, put it into your hand. Otherwise, put it on the bottom of your library.
        // −2: Target creature you control deals damage equal to its power to another target creature.
        // −7: Until end of turn, creatures you control gain "You may have this creature assign its combat damage as though it weren't blocked."
    }

    private GarrukSavageHerald(final GarrukSavageHerald card) {
        super(card);
    }

    @Override
    public GarrukSavageHerald copy() {
        return new GarrukSavageHerald(this);
    }
}
