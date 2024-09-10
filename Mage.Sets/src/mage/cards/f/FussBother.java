package mage.cards.f;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FussBother extends SplitCard {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("attacking creature you control");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public FussBother(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{2}{R/W}", "{4}{W/U}{W/U}", SpellAbilityType.SPLIT);

        // Put a +1/+1 counter on each attacking creature you control.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        );

        // Bother
        // Sorcery
        // Create three 1/1 colorless Thopter artifact creature tokens with flying. Surveil 2.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new ThopterColorlessToken(), 3));
        this.getRightHalfCard().getSpellAbility().addEffect(new SurveilEffect(2));
    }

    private FussBother(final FussBother card) {
        super(card);
    }

    @Override
    public FussBother copy() {
        return new FussBother(this);
    }
}
