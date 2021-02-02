
package mage.cards.h;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandCard;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class HourOfPromise extends CardImpl {

    public HourOfPromise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Search your library for up to two land cards and put them onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, new FilterLandCard("land cards")), true));
        // Then if you control three or more Deserts, create two 2/2 black Zombie creature tokens.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new CreateTokenEffect(new ZombieToken(), 2),
                new PermanentsOnTheBattlefieldCondition(new FilterPermanent(SubType.DESERT, "three or more Deserts"), ComparisonType.MORE_THAN, 2, true),
                "Then if you control three or more Deserts, create two 2/2 black Zombie creature tokens"));

    }

    private HourOfPromise(final HourOfPromise card) {
        super(card);
    }

    @Override
    public HourOfPromise copy() {
        return new HourOfPromise(this);
    }
}
