package mage.cards.i;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InsatiableAvarice extends CardImpl {

    public InsatiableAvarice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {2} -- Search your library for a card, then shuffle and put that card on top.
        this.getSpellAbility().addEffect(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(), false));
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(2));

        // + {B}{B} -- Target player draws three cards and loses 3 life.
        this.getSpellAbility().addMode(new Mode(new DrawCardTargetEffect(3))
                .addEffect(new LoseLifeTargetEffect(3).setText("and loses 3 life"))
                .addTarget(new TargetPlayer())
                .withCost(new ManaCostsImpl<>("{B}{B}")));
    }

    private InsatiableAvarice(final InsatiableAvarice card) {
        super(card);
    }

    @Override
    public InsatiableAvarice copy() {
        return new InsatiableAvarice(this);
    }
}
