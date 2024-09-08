package mage.cards.b;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.mana.UntilEndOfTurnManaEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrazenCollector extends CardImpl {

    public BrazenCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Brazen Collector attacks, add {R}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new AttacksTriggeredAbility(new UntilEndOfTurnManaEffect(Mana.RedMana(1))));
    }

    private BrazenCollector(final BrazenCollector card) {
        super(card);
    }

    @Override
    public BrazenCollector copy() {
        return new BrazenCollector(this);
    }
}
