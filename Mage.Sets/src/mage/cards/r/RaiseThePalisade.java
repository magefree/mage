package mage.cards.r;

import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class RaiseThePalisade extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures that aren't of the chosen type");

    static {
        filter.add(ChosenSubtypePredicate.FALSE);
    }

    public RaiseThePalisade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Choose a creature type. Return all creatures that aren't of the chosen type to their owners' hands.
        this.getSpellAbility().addEffect(new ChooseCreatureTypeEffect(Outcome.Neutral));
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(filter));
    }

    private RaiseThePalisade(final RaiseThePalisade card) {
        super(card);
    }

    @Override
    public RaiseThePalisade copy() {
        return new RaiseThePalisade(this);
    }
}
