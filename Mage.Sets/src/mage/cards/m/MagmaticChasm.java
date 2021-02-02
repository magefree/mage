
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */
public final class MagmaticChasm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public MagmaticChasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Creature without flying can't block this turn.
        this.getSpellAbility().addEffect(new CantBlockAllEffect(filter, Duration.EndOfTurn));
    }

    private MagmaticChasm(final MagmaticChasm card) {
        super(card);
    }

    @Override
    public MagmaticChasm copy() {
        return new MagmaticChasm(this);
    }
}
