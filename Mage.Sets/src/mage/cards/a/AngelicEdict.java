
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class AngelicEdict extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or enchantment");
    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.ENCHANTMENT.getPredicate()));
    }

    public AngelicEdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}");


        // Exile target creature or enchantment.
        getSpellAbility().addEffect(new ExileTargetEffect());
        getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private AngelicEdict(final AngelicEdict card) {
        super(card);
    }

    @Override
    public AngelicEdict copy() {
        return new AngelicEdict(this);
    }
}
