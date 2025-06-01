package mage.cards.f;

import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FromFatherToSon extends CardImpl {

    private static final FilterCard filter = new FilterCard("Vehicle card");

    static {
        filter.add(SubType.VEHICLE.getPredicate());
    }

    public FromFatherToSon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Search your library for a Vehicle card, reveal it, and put it into your hand. If this spell was cast from a graveyard, put that card onto the battlefield instead. Then shuffle.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)),
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true),
                CastFromGraveyardSourceCondition.instance, "search your library for a Vehicle card, " +
                "reveal it, and put it into your hand. If this spell was cast from a graveyard, " +
                "put that card onto the battlefield instead. Then shuffle"
        ));

        // Flashback {4}{W}{W}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{W}{W}{W}")));
    }

    private FromFatherToSon(final FromFatherToSon card) {
        super(card);
    }

    @Override
    public FromFatherToSon copy() {
        return new FromFatherToSon(this);
    }
}
