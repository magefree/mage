package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DuelistOfTheMind extends CardImpl {

    public DuelistOfTheMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Duelist of the Mind's power is equal to the number of cards you've drawn this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(CardsDrawnThisTurnDynamicValue.instance)
        ).addHint(CardsDrawnThisTurnDynamicValue.getHint()));

        // Whenever you commit a crime, you may draw a card. If you do, discard a card. This ability triggers only once each turn.
        this.addAbility(new CommittedCrimeTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1, true), false
        ).setTriggersOnceEachTurn(true));
    }

    private DuelistOfTheMind(final DuelistOfTheMind card) {
        super(card);
    }

    @Override
    public DuelistOfTheMind copy() {
        return new DuelistOfTheMind(this);
    }
}
