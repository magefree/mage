package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClockworkFox extends CardImpl {

    public ClockworkFox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.FOX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When leaves Clockwork Fox the battlefield, you draw two cards and each opponent draws a card.
        Ability ability = new LeavesBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(2).setText("you draw two cards"), false
        );
        ability.addEffect(new DrawCardAllEffect(1, TargetController.OPPONENT).concatBy("and"));
        this.addAbility(ability);
    }

    private ClockworkFox(final ClockworkFox card) {
        super(card);
    }

    @Override
    public ClockworkFox copy() {
        return new ClockworkFox(this);
    }
}
