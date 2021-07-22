
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class SupplyDemand extends SplitCard {

    private static final FilterCard filter = new FilterCard("multicolored card");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public SupplyDemand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{W}", "{1}{W}{U}", SpellAbilityType.SPLIT);

        // Supply
        // create X 1/1 green Saproling creature tokens.
        getLeftHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new SaprolingToken(), ManacostVariableValue.REGULAR));

        // Demand
        // Search your library for a multicolored card, reveal it, and put it into your hand. Then shuffle your library.
        getRightHalfCard().getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, filter), true));

    }

    private SupplyDemand(final SupplyDemand card) {
        super(card);
    }

    @Override
    public SupplyDemand copy() {
        return new SupplyDemand(this);
    }
}
