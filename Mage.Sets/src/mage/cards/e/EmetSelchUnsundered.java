package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author balazskristof
 */
public final class EmetSelchUnsundered extends CardImpl {

    public EmetSelchUnsundered(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.h.HadesSorcererOfEld.class;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Emet-Selch enters or attacks, draw a card, then discard a card.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));

        // At the beginning of your upkeep, if there are fourteen or more cards in your graveyard, you may transform Emet-Selch.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new TransformSourceEffect(),
                true
        ).withInterveningIf(new CardsInControllerGraveyardCondition(14)));
        this.addAbility(new TransformAbility());
    }

    private EmetSelchUnsundered(final EmetSelchUnsundered card) {
        super(card);
    }

    @Override
    public EmetSelchUnsundered copy() {
        return new EmetSelchUnsundered(this);
    }
}
