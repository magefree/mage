package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DuskLegionDuelist extends CardImpl {

    public DuskLegionDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever one or more +1/+1 counters are put on Dusk Legion Duelist, draw a card. This ability triggers only once each turn.
        this.addAbility(new OneOrMoreCountersAddedTriggeredAbility(new DrawCardSourceControllerEffect(1)).setTriggersOnceEachTurn(true));
    }

    private DuskLegionDuelist(final DuskLegionDuelist card) {
        super(card);
    }

    @Override
    public DuskLegionDuelist copy() {
        return new DuskLegionDuelist(this);
    }
}
