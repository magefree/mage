package mage.cards.t;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class TestOfLoyalty extends CardImpl {

    public TestOfLoyalty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Choose one --
        // * Plain, Simple Garak -- Exile target card from a graveyard. Draw a card.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().withFirstModeFlavorWord("Plain, Simple Garak");

        // * Of the Obsidian Order -- Return target creature card from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        mode.withFlavorWord("Of the Obsidian Order");
        this.getSpellAbility().addMode(mode);
    }

    private TestOfLoyalty(final TestOfLoyalty card) {
        super(card);
    }

    @Override
    public TestOfLoyalty copy() {
        return new TestOfLoyalty(this);
    }
}
