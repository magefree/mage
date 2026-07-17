package mage.cards.d;

import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeTargetedCardsGraveyardsEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class DennickPiousApprentice extends TransformingDoubleFacedCard {

    public DennickPiousApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER}, "{W}{U}",
                "Dennick, Pious Apparition",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT, SubType.SOLDIER}, "WU");

        // Dennick, Pious Apprentice
        this.getLeftHalfCard().setPT(2, 3);

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // Cards in graveyards can't be the targets of spells or abilities.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new CantBeTargetedCardsGraveyardsEffect()));

        // Disturb {2}{W}{U}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{2}{W}{U}"));

        // Dennick, Pious Apparition
        this.getRightHalfCard().setPT(3, 2);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever one or more creature cards are put into graveyards from anywhere, investigate. This ability triggers only once each turn.
        this.getRightHalfCard().addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new InvestigateEffect(1), false, StaticFilters.FILTER_CARD_CREATURE, TargetController.ANY
        ).setTriggersLimitEachTurn(1).setTriggerPhrase("Whenever one or more creature cards are put into graveyards from anywhere, "));

        // If Dennick, Pious Apparition would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private DennickPiousApprentice(final DennickPiousApprentice card) {
        super(card);
    }

    @Override
    public DennickPiousApprentice copy() {
        return new DennickPiousApprentice(this);
    }
}
