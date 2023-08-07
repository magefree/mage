package mage.cards.t;

import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheBattleOfBywater extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creatures with power 3 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public TheBattleOfBywater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Destroy all creatures with power 3 or greater. Then create a Food token for each creature you control.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new FoodToken(), CreaturesYouControlCount.instance
        ).setText("Then create a Food token for each creature you control"));
    }

    private TheBattleOfBywater(final TheBattleOfBywater card) {
        super(card);
    }

    @Override
    public TheBattleOfBywater copy() {
        return new TheBattleOfBywater(this);
    }
}
