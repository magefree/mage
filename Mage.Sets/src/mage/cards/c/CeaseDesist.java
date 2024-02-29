package mage.cards.c;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CeaseDesist extends SplitCard {
    public CeaseDesist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{1}{B/G}", "{4}{G/W}{G/W}", SpellAbilityType.SPLIT);

        // Exile up to two target cards from a single graveyard. Target player gains 2 life and draws a card.
        getLeftHalfCard().getSpellAbility().addEffect(new ExileTargetEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCardInASingleGraveyard(0, 2, StaticFilters.FILTER_CARD_CARDS));
        getLeftHalfCard().getSpellAbility().addEffect(new GainLifeTargetEffect(2).setTargetPointer(new SecondTargetPointer()));
        getLeftHalfCard().getSpellAbility().addEffect(
                new DrawCardTargetEffect(1)
                        .setTargetPointer(new SecondTargetPointer())
                        .setText("and draws a card")
        );
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPlayer());

        // Destroy all artifacts and enchantments.
        getRightHalfCard().getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_ARTIFACTS_AND_ENCHANTMENTS));
    }

    private CeaseDesist(final CeaseDesist card) {
        super(card);
    }

    @Override
    public CeaseDesist copy() {
        return new CeaseDesist(this);
    }
}
