package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author mpouedras, notgreat
 */
public final class BroodOfCockroaches extends CardImpl {

    public BroodOfCockroaches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Brood of Cockroaches is put into your graveyard from the battlefield,
        // at the beginning of the next end step, you lose 1 life and return Brood of Cockroaches to your hand.

        AtTheBeginOfNextEndStepDelayedTriggeredAbility delayed =
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new LoseLifeSourceControllerEffect(1));
        delayed.addEffect(new ReturnSourceFromGraveyardToHandEffect().concatBy("and").setText("return this card to your hand"));
        CreateDelayedTriggeredAbilityEffect delayedEffect = new CreateDelayedTriggeredAbilityEffect(delayed);

        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(delayedEffect, false, true));
    }

    private BroodOfCockroaches(final BroodOfCockroaches card) {
        super(card);
    }

    @Override
    public BroodOfCockroaches copy() {
        return new BroodOfCockroaches(this);
    }
}
