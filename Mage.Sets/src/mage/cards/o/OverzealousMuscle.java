package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OverzealousMuscle extends CardImpl {

    public OverzealousMuscle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever you commit a crime during your turn, Overzealous Muscle gains indestructible until end of turn.
        this.addAbility(new OverzealousMuscleTriggeredAbility());
    }

    private OverzealousMuscle(final OverzealousMuscle card) {
        super(card);
    }

    @Override
    public OverzealousMuscle copy() {
        return new OverzealousMuscle(this);
    }
}

class OverzealousMuscleTriggeredAbility extends CommittedCrimeTriggeredAbility {

    OverzealousMuscleTriggeredAbility() {
        super(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn));
        this.setTriggerPhrase("Whenever you commit a crime during your turn, ");
    }

    private OverzealousMuscleTriggeredAbility(final OverzealousMuscleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OverzealousMuscleTriggeredAbility copy() {
        return new OverzealousMuscleTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return super.checkTrigger(event, game)
                && game.isActivePlayer(getControllerId());
    }

}