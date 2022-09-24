package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.ExtortAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LifeInsurance extends CardImpl {

    public LifeInsurance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{B}");

        // Extort
        this.addAbility(new ExtortAbility());

        // Whenever a nontoken creature dies, you lose 1 life and create a Treasure token.
        Ability ability = new DiesCreatureTriggeredAbility(
                new LoseLifeSourceControllerEffect(1), false, StaticFilters.FILTER_CREATURE_NON_TOKEN
        );
        ability.addEffect(new CreateTokenEffect(new TreasureToken()).concatBy("and"));
        this.addAbility(ability);
    }

    private LifeInsurance(final LifeInsurance card) {
        super(card);
    }

    @Override
    public LifeInsurance copy() {
        return new LifeInsurance(this);
    }
}
