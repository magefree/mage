package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrystallineResonance extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public CrystallineResonance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever you cycle a card, you may have Crystalline Resonance become a copy of another target permanent until your next turn, except it has this ability.
        this.addAbility(CrystallineResonance.createAbility());
    }

    private CrystallineResonance(final CrystallineResonance card) {
        super(card);
    }

    @Override
    public CrystallineResonance copy() {
        return new CrystallineResonance(this);
    }

    static Ability createAbility() {
        Ability ability = new CycleControllerTriggeredAbility(
                new CopyPermanentEffect(
                        StaticFilters.FILTER_PERMANENT_CREATURE,
                        new CrystallineResonanceCopyApplier(), true
                ).setDuration(Duration.UntilYourNextTurn).setText(
                        "have {this} become a copy of another target permanent until your next turn, " +
                                "except it has this ability"
                ), true
        );
        ability.addTarget(new TargetPermanent(filter));
        return ability;
    }
}

class CrystallineResonanceCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.getAbilities().add(CrystallineResonance.createAbility());
        return true;
    }
}
