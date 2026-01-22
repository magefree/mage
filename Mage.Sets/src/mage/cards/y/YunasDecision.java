package mage.cards.y;

import mage.abilities.Mode;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCreatureAndOrLandFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YunasDecision extends CardImpl {

    public YunasDecision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Choose one --
        // * Continue the Pilgrimage -- Sacrifice a creature. If you do, draw a card, then you may put a creature card and/or a land card from your hand onto the battlefield.
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE), null, false
        ).addEffect(new PutCreatureAndOrLandFromHandOntoBattlefieldEffect().concatBy(", then")));
        this.getSpellAbility().withFirstModeFlavorWord("Continue the Pilgrimage");

        // * Find Another Way -- Return one or two target permanent cards from your graveyard to your hand.
        this.getSpellAbility().addMode(
                new Mode(new ReturnFromGraveyardToHandTargetEffect())
                        .addTarget(new TargetCardInYourGraveyard(
                                1, 2, StaticFilters.FILTER_CARD_PERMANENTS
                        ))
                        .withFlavorWord("Find Another Way")
        );
    }

    private YunasDecision(final YunasDecision card) {
        super(card);
    }

    @Override
    public YunasDecision copy() {
        return new YunasDecision(this);
    }
}
