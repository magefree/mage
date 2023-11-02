package mage.cards.h;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HelpingHand extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public HelpingHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Return target creature card with mana value 3 or less from your graveyard to the battlefield tapped.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private HelpingHand(final HelpingHand card) {
        super(card);
    }

    @Override
    public HelpingHand copy() {
        return new HelpingHand(this);
    }
}
