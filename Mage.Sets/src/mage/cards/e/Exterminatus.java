package mage.cards.e;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Exterminatus extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public Exterminatus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}{B}");

        // Nonland permanents your opponents control lose indestructible until end of turn.
        this.getSpellAbility().addEffect(new LoseAbilityAllEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("nonland permanents your opponents control lose indestructible until end of turn"));

        // Destroy all nonland permanents.
        this.getSpellAbility().addEffect(new DestroyAllEffect(
                StaticFilters.FILTER_PERMANENTS_NON_LAND
        ).concatBy("<br>"));
    }

    private Exterminatus(final Exterminatus card) {
        super(card);
    }

    @Override
    public Exterminatus copy() {
        return new Exterminatus(this);
    }
}
