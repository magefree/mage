package mage.cards.m;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CreaturePutInYourGraveyardCondition;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.CreaturePutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MacabreReconstruction extends CardImpl {

    public MacabreReconstruction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // This spell costs {2} less to cast if a creature card was put into your graveyard from anywhere this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, CreaturePutInYourGraveyardCondition.instance)
        ).setRuleAtTheTop(true).addHint(CreaturePutInYourGraveyardCondition.getHint()), new CreaturePutIntoGraveyardWatcher());

        // Return up to two target creature cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 2, StaticFilters.FILTER_CARD_CREATURES
        ));
    }

    private MacabreReconstruction(final MacabreReconstruction card) {
        super(card);
    }

    @Override
    public MacabreReconstruction copy() {
        return new MacabreReconstruction(this);
    }
}
