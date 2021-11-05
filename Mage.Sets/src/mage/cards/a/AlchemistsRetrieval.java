package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.CleaveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlchemistsRetrieval extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent [you control]");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public AlchemistsRetrieval(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Cleave {1}{U}
        Ability ability = new CleaveAbility(this, new ReturnToHandTargetEffect(), "{1}{U}");
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);

        // Return target nonland permanent [you control] to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private AlchemistsRetrieval(final AlchemistsRetrieval card) {
        super(card);
    }

    @Override
    public AlchemistsRetrieval copy() {
        return new AlchemistsRetrieval(this);
    }
}
