package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class RiversGrasp extends CardImpl {

    public RiversGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U/B}");

        // If {U} was spent to cast River's Grasp, return up to one target creature to its owner's hand. If {B} was spent to cast River's Grasp, target player reveals their hand, you choose a nonland card from it, then that player discards that card.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ReturnToHandTargetEffect(),
                new ManaWasSpentCondition(ColoredManaSymbol.U),
                "If {U} was spent to cast this spell, return up to one target creature to its owner's hand"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND, TargetController.ANY),
                new ManaWasSpentCondition(ColoredManaSymbol.B),
                " If {B} was spent to cast this spell, target player reveals their hand, you choose a nonland card from it, then that player discards that card")
                .setTargetPointer(new SecondTargetPointer()));

        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().addTarget(new TargetPlayer());

        this.getSpellAbility().addEffect(new InfoEffect("<i>(Do both if {U}{B} was spent.)</i>"));
    }

    private RiversGrasp(final RiversGrasp card) {
        super(card);
    }

    @Override
    public RiversGrasp copy() {
        return new RiversGrasp(this);
    }
}
