package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnderdarkExplorer extends CardImpl {

    public UnderdarkExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Underdark Explorer enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));
    }

    private UnderdarkExplorer(final UnderdarkExplorer card) {
        super(card);
    }

    @Override
    public UnderdarkExplorer copy() {
        return new UnderdarkExplorer(this);
    }
}
