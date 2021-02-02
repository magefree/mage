

package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

/**
 *
 * @author LevelX2
 */


public final class AweForTheGuilds extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Monocolored creatures");
    static {
        filter.add(Predicates.not(MulticoloredPredicate.instance));
    }

    public AweForTheGuilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");


        // Monocolored creatures can't block this turn.
        this.getSpellAbility().addEffect(new CantBlockAllEffect(filter, Duration.EndOfTurn));

    }

    private AweForTheGuilds(final AweForTheGuilds card) {
        super(card);
    }

    @Override
    public AweForTheGuilds copy() {
        return new AweForTheGuilds(this);
    }

}
