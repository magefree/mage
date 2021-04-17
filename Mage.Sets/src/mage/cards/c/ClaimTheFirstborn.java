package mage.cards.c;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClaimTheFirstborn extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public ClaimTheFirstborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Gain control of target creature with converted mana cost 3 or less until end of turn. Untap that creature. It gains haste until end of turn.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect()
                .setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                .setText("It gains haste until end of turn."));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private ClaimTheFirstborn(final ClaimTheFirstborn card) {
        super(card);
    }

    @Override
    public ClaimTheFirstborn copy() {
        return new ClaimTheFirstborn(this);
    }
}
