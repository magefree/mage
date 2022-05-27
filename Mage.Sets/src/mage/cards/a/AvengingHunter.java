package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvengingHunter extends CardImpl {

    public AvengingHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Avenging Hunter enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));
    }

    private AvengingHunter(final AvengingHunter card) {
        super(card);
    }

    @Override
    public AvengingHunter copy() {
        return new AvengingHunter(this);
    }
}
