
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author Styxo
 */
public final class ForceScream extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without spaceflight");

    static {
        filter.add(Predicates.not(new AbilityPredicate(SpaceflightAbility.class)));

    }

    public ForceScream(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Force Spark deals 2 damage to each creature without spaceflight and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(2, filter));

        // Scry 1.
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private ForceScream(final ForceScream card) {
        super(card);
    }

    @Override
    public ForceScream copy() {
        return new ForceScream(this);
    }
}
