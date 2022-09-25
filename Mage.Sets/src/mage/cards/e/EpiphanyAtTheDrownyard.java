package mage.cards.e;

import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.RevealAndSeparatePilesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EpiphanyAtTheDrownyard extends CardImpl {

    private static final DynamicValue xValue = new AdditiveDynamicValue(ManacostVariableValue.REGULAR, StaticValue.get(1));

    public EpiphanyAtTheDrownyard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Reveal the top X plus one cards of your library and separate them into two piles. An opponent chooses one of those piles. Put that pile into your hand and the other into your graveyard.
        this.getSpellAbility().addEffect(new RevealAndSeparatePilesEffect(
                xValue, TargetController.YOU, TargetController.OPPONENT, Zone.GRAVEYARD
        ).setText("reveal the top X plus one cards of your library and separate them into two piles. " +
                "An opponent chooses one of those piles. Put that pile into your hand and the other into your graveyard"));
    }

    private EpiphanyAtTheDrownyard(final EpiphanyAtTheDrownyard card) {
        super(card);
    }

    @Override
    public EpiphanyAtTheDrownyard copy() {
        return new EpiphanyAtTheDrownyard(this);
    }
}
