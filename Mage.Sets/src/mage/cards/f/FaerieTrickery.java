package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.PutCards;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public final class FaerieTrickery extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("non-Faerie spell");

    static {
        filter.add(Predicates.not(SubType.FAERIE.getPredicate()));
    }

    public FaerieTrickery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{1}{U}{U}");
        this.subtype.add(SubType.FAERIE);


        // Counter target non-Faerie spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(PutCards.EXILED));
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private FaerieTrickery(final FaerieTrickery card) {
        super(card);
    }

    @Override
    public FaerieTrickery copy() {
        return new FaerieTrickery(this);
    }
}
