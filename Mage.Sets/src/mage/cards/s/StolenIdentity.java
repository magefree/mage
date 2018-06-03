
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class StolenIdentity extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.CREATURE)));
    }

    public StolenIdentity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}{U}");

        // Create a token that's a copy of target artifact or creature.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        // Cipher
        this.getSpellAbility().addEffect(new CipherEffect());
    }

    public StolenIdentity(final StolenIdentity card) {
        super(card);
    }

    @Override
    public StolenIdentity copy() {
        return new StolenIdentity(this);
    }
}
