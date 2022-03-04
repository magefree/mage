package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.CleaveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WashAway extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell [that wasn't cast from its owner's hand]");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public WashAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Cleave {1}{U}{U}
        Ability ability = new CleaveAbility(this, new CounterTargetEffect(), "{1}{U}{U}");
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);

        // Counter target spell [that wasn't cast from its owner's hand].
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private WashAway(final WashAway card) {
        super(card);
    }

    @Override
    public WashAway copy() {
        return new WashAway(this);
    }
}
