package mage.cards.r;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuinousUltimatum extends CardImpl {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("nonland permanents your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public RuinousUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{R}{W}{W}{W}{B}{B}");

        // Destroy all nonland permanents your opponents control
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private RuinousUltimatum(final RuinousUltimatum card) {
        super(card);
    }

    @Override
    public RuinousUltimatum copy() {
        return new RuinousUltimatum(this);
    }
}
