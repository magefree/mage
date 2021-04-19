package mage.cards.r;

import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Reanimate extends CardImpl {

    public Reanimate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its converted mana cost.
        getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("put target creature card from a graveyard onto the battlefield under your control"));
        Effect effect = new LoseLifeSourceControllerEffect(TargetManaValue.instance);
        effect.setText("You lose life equal to its mana value");
        getSpellAbility().addEffect(effect);
    }

    private Reanimate(final Reanimate card) {
        super(card);
    }

    @Override
    public Reanimate copy() {
        return new Reanimate(this);
    }
}
