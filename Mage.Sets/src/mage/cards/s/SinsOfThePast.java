package mage.cards.s;

import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.MayCastTargetThenExileEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SinsOfThePast extends CardImpl {

    public SinsOfThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Until end of turn, you may cast target instant or sorcery card from your graveyard without paying its mana cost. If that card would be put into your graveyard this turn, exile it instead. Exile Sins of the Past.
        this.getSpellAbility().addEffect(new MayCastTargetThenExileEffect(true)
                .setText("Until end of turn, you may cast target instant or sorcery card from your graveyard without paying its mana cost. "
                        + ThatSpellGraveyardExileReplacementEffect.RULE_YOUR));
        this.getSpellAbility().addEffect(new ExileSpellEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
    }

    private SinsOfThePast(final SinsOfThePast card) {
        super(card);
    }

    @Override
    public SinsOfThePast copy() {
        return new SinsOfThePast(this);
    }
}
