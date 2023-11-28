package mage.cards.r;

import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RunForYourLife extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(new AbilityPredicate(HasteAbility.class)));
    }

    public RunForYourLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{R}");

        // One or two target creatures each gain haste until end of turn. They can't be blocked this turn except by creatures with haste.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("one or two target creatures each gain haste until end of turn"));
        this.getSpellAbility().addEffect(new CantBeBlockedTargetEffect(filter, Duration.EndOfTurn)
                .setText("They can't be blocked this turn except by creatures with haste"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1, 2));

        // Escape--{2}{U}{R}, Exile four other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{2}{U}{R}", 4));
    }

    private RunForYourLife(final RunForYourLife card) {
        super(card);
    }

    @Override
    public RunForYourLife copy() {
        return new RunForYourLife(this);
    }
}
