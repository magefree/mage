package mage.cards.d;

import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.GiftType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DewdropCure extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature cards each with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public DewdropCure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Gift a card
        this.addAbility(new GiftAbility(this, GiftType.CARD));

        // Return up to two target creature cards each with mana value 2 or less from your graveyard to the battlefield. If the gift was promised, instead return up to three target creature cards each with mana value 2 or less from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addEffect(new InfoEffect(
                "If the gift was promised, instead return up to three target creature cards " +
                        "each with mana value 2 or less from your graveyard to the battlefield"
        ));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, filter));
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(
                GiftWasPromisedCondition.TRUE, new TargetCardInYourGraveyard(0, 3, filter)
        ));
    }

    private DewdropCure(final DewdropCure card) {
        super(card);
    }

    @Override
    public DewdropCure copy() {
        return new DewdropCure(this);
    }
}
