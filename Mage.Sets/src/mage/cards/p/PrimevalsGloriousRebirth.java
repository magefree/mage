package mage.cards.p;

import mage.abilities.common.LegendarySpellAbility;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterPermanentCard;

import java.util.UUID;

/**
 * @author JRHerlehy Created on 4/8/18.
 */
public final class PrimevalsGloriousRebirth extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("legendary permanent cards");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public PrimevalsGloriousRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);

        // (You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)
        this.addAbility(new LegendarySpellAbility());

        // Return all legendary permanent cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromYourGraveyardToBattlefieldAllEffect(filter));
    }

    private PrimevalsGloriousRebirth(final PrimevalsGloriousRebirth card) {
        super(card);
    }

    @Override
    public PrimevalsGloriousRebirth copy() {
        return new PrimevalsGloriousRebirth(this);
    }

}
