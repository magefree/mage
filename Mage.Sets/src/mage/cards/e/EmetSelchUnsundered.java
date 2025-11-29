package mage.cards.e;

import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author balazskristof
 */
public final class EmetSelchUnsundered extends TransformingDoubleFacedCard {

    public EmetSelchUnsundered(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDER, SubType.WIZARD}, "{1}{U}{B}",
                "Hades, Sorcerer of Eld",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.AVATAR}, ""
        );

        // Emet-Selch, Unsundered
        this.getLeftHalfCard().setPT(2, 4);

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // Whenever Emet-Selch enters or attacks, draw a card, then discard a card.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));

        // At the beginning of your upkeep, if there are fourteen or more cards in your graveyard, you may transform Emet-Selch.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                new TransformSourceEffect(),
                true
        ).withInterveningIf(new CardsInControllerGraveyardCondition(14))); // assumption: condition counts all cards

        // Hades, Sorcerer of Eld
        this.getRightHalfCard().setPT(6, 6);
        this.getRightHalfCard().getColor().setBlue(true);
        this.getRightHalfCard().getColor().setBlack(true);

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Echo of the Lost -- During your turn, you may play cards from your graveyard.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                PlayFromGraveyardControllerEffect.playCards(), MyTurnCondition.instance
        ).setText("during your turn, you may play cards from your graveyard")).withFlavorWord("Echo of the Lost"));

        // If a card or token would be put into your graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GraveyardFromAnywhereExileReplacementEffect(true, true)));
    }

    private EmetSelchUnsundered(final EmetSelchUnsundered card) {
        super(card);
    }

    @Override
    public EmetSelchUnsundered copy() {
        return new EmetSelchUnsundered(this);
    }
}
