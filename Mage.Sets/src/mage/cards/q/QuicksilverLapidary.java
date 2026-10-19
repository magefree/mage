package mage.cards.q;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

public final class QuicksilverLapidary extends CardImpl {

    public QuicksilverLapidary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConjureCardEffect("Mox Opal")));
    }

    private QuicksilverLapidary(final QuicksilverLapidary card) {
        super(card);
    }

    @Override
    public QuicksilverLapidary copy() {
        return new QuicksilverLapidary(this);
    }
}
