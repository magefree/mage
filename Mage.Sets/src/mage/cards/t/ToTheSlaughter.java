package mage.cards.t;

import java.util.UUID;

import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPlayer;

/**
 * @author fireshoes
 */
public final class ToTheSlaughter extends CardImpl {

    public ToTheSlaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Target player sacrifices a creature or planeswalker.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SacrificeEffect(new FilterCreatureOrPlaneswalkerPermanent(), 1, "Target player"),
                new InvertCondition(DeliriumCondition.instance),
                "Target player sacrifices a creature or planeswalker."));

        // <i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, instead that player sacrifices a creature and a planeswalker.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "Target player"),
                DeliriumCondition.instance,
                "<br><i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, instead that player sacrifices a creature"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SacrificeEffect(StaticFilters.FILTER_PERMANENT_PLANESWALKER, 1, "Target player"),
                DeliriumCondition.instance, "and a planeswalker."));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addHint(CardTypesInGraveyardHint.YOU);
    }

    private ToTheSlaughter(final ToTheSlaughter card) {
        super(card);
    }

    @Override
    public ToTheSlaughter copy() {
        return new ToTheSlaughter(this);
    }
}
