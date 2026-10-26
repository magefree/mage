package mage.cards.d;

import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValueParityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DesecrateReality extends CardImpl {

    private static final FilterPermanent evenFilter = new FilterPermanent("permanent with an even mana value");
    private static final FilterCard oddFilter
        = new FilterPermanentCard("permanent card with an odd mana value in your graveyard");

    static {
        evenFilter.add(ManaValueParityPredicate.EVEN);
        oddFilter.add(ManaValueParityPredicate.ODD);
    }
    public DesecrateReality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{7}");


        // For each opponent, exile up to one target permanent that player controls with an even mana value.
        this.getSpellAbility().addEffect(new ExileTargetEffect()
            .setTargetPointer(new EachTargetPointer())
            .setText("for each opponent, exile up to one target permanent that player controls with an even mana value."));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, evenFilter));
        this.getSpellAbility().setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));

        // Adamant -- If at least three colorless mana was spent to cast this spell, return a permanent card with an odd mana value from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new OneShotNonTargetEffect(
                new ReturnFromGraveyardToBattlefieldTargetEffect()
                    .setText("return a permanent card with an odd mana value from your graveyard to the battlefield"),
                new TargetCardInYourGraveyard(oddFilter)
            ),
            AdamantCondition.COLORLESS, "<br><i>Adamant</i> &mdash; "
            + "If at least three colorless mana was spent to cast this spell, "
            + "return a permanent card with an odd mana value from your graveyard to the battlefield."
        ));
    }

    private DesecrateReality(final DesecrateReality card) {
        super(card);
    }

    @Override
    public DesecrateReality copy() {
        return new DesecrateReality(this);
    }
}
