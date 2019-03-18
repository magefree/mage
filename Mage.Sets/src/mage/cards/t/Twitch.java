
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Twitch extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or land");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    public Twitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");


        // You may tap or untap target artifact, creature, or land.
        // Draw a card.
        this.getSpellAbility().addEffect(new MayTapOrUntapTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public Twitch(final Twitch card) {
        super(card);
    }

    @Override
    public Twitch copy() {
        return new Twitch(this);
    }
}
