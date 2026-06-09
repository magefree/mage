package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CruelSomnophage extends AdventureCard {

    private static final DynamicValue xValue = new CardsInAllGraveyardsCount(StaticFilters.FILTER_CARD_CREATURES);

    public CruelSomnophage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.NIGHTMARE}, "{1}{B}",
                "Can't Wake Up",
                new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Cruel Somnophage
        this.getLeftHalfCard().setPT(0, 0);

        // Cruel Somnophage's power and toughness are each equal to the number of creature cards in all graveyards.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue)));

        // Can't Wake Up
        // Target player mills four cards.
        this.getRightHalfCard().getSpellAbility().addEffect(new MillCardsTargetEffect(4));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer());

        finalizeCard();
    }

    private CruelSomnophage(final CruelSomnophage card) {
        super(card);
    }

    @Override
    public CruelSomnophage copy() {
        return new CruelSomnophage(this);
    }
}
