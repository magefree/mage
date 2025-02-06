package mage.cards.r;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NoAbilityPredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiseFromTheWreck extends CardImpl {

    private static final FilterCard filter = new FilterCard("Mount card");
    private static final FilterCard filter2 = new FilterCard("Vehicle card");
    private static final FilterCard filter3 = new FilterCard("creature card with no abilities");

    static {
        filter.add(SubType.MOUNT.getPredicate());
        filter2.add(SubType.VEHICLE.getPredicate());
        filter3.add(NoAbilityPredicate.instance);
    }

    public RiseFromTheWreck(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Return up to one target creature card, up to one target Mount card, up to one target Vehicle card, and up to one target creature card with no abilities from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect()
                .setTargetPointer(new EachTargetPointer())
                .setText("return up to one target creature card, up to one target Mount card, " +
                        "up to one target Vehicle card, and up to one target creature card " +
                        "with no abilities from your graveyard to your hand"));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filter2));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, filter3));
    }

    private RiseFromTheWreck(final RiseFromTheWreck card) {
        super(card);
    }

    @Override
    public RiseFromTheWreck copy() {
        return new RiseFromTheWreck(this);
    }
}
