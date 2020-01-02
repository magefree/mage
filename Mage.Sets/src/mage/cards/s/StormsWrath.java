package mage.cards.s;

import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormsWrath extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature and each planeswalker");

    public StormsWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Storm's Wrath deals 4 damage to each creature and each planeswalker.
        this.getSpellAbility().addEffect(new DamageAllEffect(4, filter));
    }

    private StormsWrath(final StormsWrath card) {
        super(card);
    }

    @Override
    public StormsWrath copy() {
        return new StormsWrath(this);
    }
}
