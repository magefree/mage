package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrborgRepossession extends CardImpl {

    public UrborgRepossession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Kicker {1}{G}
        this.addAbility(new KickerAbility("{1}{G}"));

        // Return target creature card from your graveyard to your hand. You gain 2 life. If this spell was kicked, return another target permanent card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect()
                .setTargetPointer(new EachTargetPointer())
                .setText("return target creature card from your graveyard to your hand"));
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addEffect(new InfoEffect("If this spell was kicked, " +
                "return another target permanent card from your graveyard to your hand"));
        this.getSpellAbility().setTargetAdjuster(UrborgRepossessionAdjuster.instance);
    }

    private UrborgRepossession(final UrborgRepossession card) {
        super(card);
    }

    @Override
    public UrborgRepossession copy() {
        return new UrborgRepossession(this);
    }
}

enum UrborgRepossessionAdjuster implements TargetAdjuster {
    instance;
    private static final FilterCard filter = new FilterPermanentCard("another permanent card from your graveyard");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        if (KickedCondition.ONCE.apply(game, ability)) {
            ability.addTarget(new TargetCardInYourGraveyard(filter).setTargetTag(2));
        }
    }
}
