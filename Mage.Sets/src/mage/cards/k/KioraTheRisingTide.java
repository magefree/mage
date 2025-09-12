package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.ScionOfTheDeepToken;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class KioraTheRisingTide extends CardImpl {

    public KioraTheRisingTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Kiora enters, draw two cards, then discard two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawDiscardControllerEffect(2, 2)
        ));

        // Threshold -- Whenever Kiora attacks, if there are seven or more cards in your graveyard, you may create Scion of the Deep, a legendary 8/8 blue Octopus creature token.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new ScionOfTheDeepToken()), true)
                .withInterveningIf(ThresholdCondition.instance).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private KioraTheRisingTide(final KioraTheRisingTide card) {
        super(card);
    }

    @Override
    public KioraTheRisingTide copy() {
        return new KioraTheRisingTide(this);
    }
}
