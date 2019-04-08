package mage.cards.r;

import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavnicaAtWar extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("multicolored permanents");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public RavnicaAtWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Exile all multicolored permanents.
        this.getSpellAbility().addEffect(new ExileAllEffect(filter));
    }

    private RavnicaAtWar(final RavnicaAtWar card) {
        super(card);
    }

    @Override
    public RavnicaAtWar copy() {
        return new RavnicaAtWar(this);
    }
}
