package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.ServoToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SramsExpertise extends CardImpl {

    private static final FilterCard filter = new FilterCard("a spell with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public SramsExpertise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Create three 1/1 colorless Servo artifact creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ServoToken(), 3));

        // You may cast a card with converted mana cost 3 or less from your hand without paying its mana cost.
        this.getSpellAbility().addEffect(new CastFromHandForFreeEffect(filter).concatBy("<br>"));
    }

    private SramsExpertise(final SramsExpertise card) {
        super(card);
    }

    @Override
    public SramsExpertise copy() {
        return new SramsExpertise(this);
    }
}
