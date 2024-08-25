package mage.cards.c;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CalamityOfCinders extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("untapped creature");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public CalamityOfCinders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Calamity of Cinders deals 6 damage to each untapped creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(6, filter));
    }

    private CalamityOfCinders(final CalamityOfCinders card) {
        super(card);
    }

    @Override
    public CalamityOfCinders copy() {
        return new CalamityOfCinders(this);
    }
}
