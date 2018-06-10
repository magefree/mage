
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author fireshoes
 */
public final class FieldOfSouls extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");
    static{
        filter.add(new OwnerPredicate(TargetController.YOU));
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public FieldOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // Whenever a nontoken creature is put into your graveyard from the battlefield, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken()), false, filter));
    }

    public FieldOfSouls(final FieldOfSouls card) {
        super(card);
    }

    @Override
    public FieldOfSouls copy() {
        return new FieldOfSouls(this);
    }
}
