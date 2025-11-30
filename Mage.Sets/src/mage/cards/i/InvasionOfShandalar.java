package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfShandalar extends TransformingDoubleFacedCard {

    public InvasionOfShandalar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{3}{G}{G}",
                "Leyline Surge",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "G"
        );

        // Invasion of Shandalar
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Shandalar enters the battlefield, return up to three target permanent cards from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0, 3, StaticFilters.FILTER_CARD_PERMANENTS));
        this.getLeftHalfCard().addAbility(ability);

        // Leyline Surge
        // At the beginning of your upkeep, you may put a permanent card from your hand onto the battlefield.
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(
                StaticFilters.FILTER_CARD_A_PERMANENT
        )));
    }

    private InvasionOfShandalar(final InvasionOfShandalar card) {
        super(card);
    }

    @Override
    public InvasionOfShandalar copy() {
        return new InvasionOfShandalar(this);
    }
}
