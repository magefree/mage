package mage.cards.g;

import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.MapToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GetLost extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature, enchantment, or planeswalker");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public GetLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Destroy target creature, enchantment, or planeswalker. Its controller creates two Map tokens.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetPermanentEffect(new MapToken(), 2, false));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private GetLost(final GetLost card) {
        super(card);
    }

    @Override
    public GetLost copy() {
        return new GetLost(this);
    }
}