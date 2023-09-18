package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.EachTargetPointer;

/**
 * @author fireshoes
 */
public final class CropSigil extends CardImpl {

    public CropSigil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // At the beginning of your upkeep, you may mill a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MillCardsControllerEffect(1), TargetController.YOU, true));

        // <i>Delirium</i> &mdash; {2}{G}, Sacrifice Crop Sigil: Return up to one target creature card and up to one target land card from your graveyard to your hand.
        // Activate this ability only if there are four or more card types among cards in your graveyard.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new ReturnFromGraveyardToHandTargetEffect().setTargetPointer(new EachTargetPointer()),
                new ManaCostsImpl<>("{2}{G}"),
                DeliriumCondition.instance);
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_LAND));
        ability.setAbilityWord(AbilityWord.DELIRIUM);
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);
    }

    private CropSigil(final CropSigil card) {
        super(card);
    }

    @Override
    public CropSigil copy() {
        return new CropSigil(this);
    }
}
