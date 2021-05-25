package mage.cards.d;

import java.util.UUID;

import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AngelToken;

/**
 * @author fireshoes
 */
public final class DescendUponTheSinful extends CardImpl {

    public DescendUponTheSinful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Exile all creatures
        this.getSpellAbility().addEffect(new ExileAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));

        // <i>Delirium</i> &mdash; Create a 4/4 white Angel creature token with flying if there are four or more card types among cards in your graveyard.
        Effect effect = new ConditionalOneShotEffect(new CreateTokenEffect(new AngelToken()), DeliriumCondition.instance);
        effect.setText("<br/><i>Delirium</i> &mdash; Create a 4/4 white Angel creature token with flying if there are four or more card types among cards in your graveyard");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addHint(CardTypesInGraveyardHint.YOU);
    }

    private DescendUponTheSinful(final DescendUponTheSinful card) {
        super(card);
    }

    @Override
    public DescendUponTheSinful copy() {
        return new DescendUponTheSinful(this);
    }
}
