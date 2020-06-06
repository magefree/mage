package mage.cards.c;

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
public final class ChandraFlamesCatalyst extends CardImpl {

    public ChandraFlamesCatalyst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));

        // +1: Chandra, Flame's Catalyst deals 3 damage to each opponent.
        // −2: You may cast target red instant or sorcery card from your graveyard. If that spell would be put into your graveyard this turn, exile it instead.
        // −8: Discard your hand, then draw seven cards. Until end of turn, you may cast spells from your hand without paying their mana costs.
    }

    private ChandraFlamesCatalyst(final ChandraFlamesCatalyst card) {
        super(card);
    }

    @Override
    public ChandraFlamesCatalyst copy() {
        return new ChandraFlamesCatalyst(this);
    }
}
