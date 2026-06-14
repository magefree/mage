package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author muz
 */
public final class TheArkenstone extends AdventureCard {

    private static final FilterCard filter = new FilterCreatureCard("legendary creature card");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public TheArkenstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, new CardType[]{CardType.SORCERY}, "{5}", "Seek the Heart", "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)));

        // At the beginning of your end step, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Seek the Heart
        // Sorcery {2}{W}
        // Search your library for a legendary creature card, reveal it, put it into your hand, then shuffle.
        this.getSpellCard().getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true));

        this.finalizeAdventure();
    }

    private TheArkenstone(final TheArkenstone card) {
        super(card);
    }

    @Override
    public TheArkenstone copy() {
        return new TheArkenstone(this);
    }
}
