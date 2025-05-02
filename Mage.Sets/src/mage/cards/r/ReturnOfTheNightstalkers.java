package mage.cards.r;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterPermanentCard;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class ReturnOfTheNightstalkers extends CardImpl {

    private static final FilterPermanentCard filter1 = new FilterPermanentCard("Nightstalker permanent cards");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("Swamps you control");

    static {
        filter1.add(SubType.NIGHTSTALKER.getPredicate());
        filter2.add(SubType.SWAMP.getPredicate());
    }

    public ReturnOfTheNightstalkers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        // Return all Nightstalker permanent cards from your graveyard to the battlefield. Then destroy all Swamps you control.
        this.getSpellAbility().addEffect(new ReturnFromYourGraveyardToBattlefieldAllEffect(filter1));
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter2).concatBy("Then"));
    }

    private ReturnOfTheNightstalkers(final ReturnOfTheNightstalkers card) {
        super(card);
    }

    @Override
    public ReturnOfTheNightstalkers copy() {
        return new ReturnOfTheNightstalkers(this);
    }
}
