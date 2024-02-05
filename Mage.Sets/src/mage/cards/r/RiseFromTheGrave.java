package mage.cards.r;

import mage.ObjectColor;
import mage.abilities.effects.common.PutOntoBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class RiseFromTheGrave extends CardImpl {

    public RiseFromTheGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");

        // Put target creature card from a graveyard onto the battlefield under your control. That creature is a black Zombie in addition to its other colors and types.
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD));
        this.getSpellAbility().addEffect(new PutOntoBattlefieldTargetEffect(false)
                .withContinuousEffects("That creature is a black Zombie in addition to its other colors and types",
                        new BecomesColorTargetEffect(ObjectColor.BLACK, true, Duration.Custom),
                        new AddCardSubTypeTargetEffect(SubType.ZOMBIE, Duration.Custom)));
    }

    private RiseFromTheGrave(final RiseFromTheGrave card) {
        super(card);
    }

    @Override
    public RiseFromTheGrave copy() {
        return new RiseFromTheGrave(this);
    }
}
