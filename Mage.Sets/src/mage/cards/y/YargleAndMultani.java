package mage.cards.y;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

public class YargleAndMultani extends CardImpl {
    public YargleAndMultani(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.FROG);
        this.addSubType(SubType.SPIRIT);
        this.addSubType(SubType.ELEMENTAL);
        this.power = new MageInt(18);
        this.toughness = new MageInt(6);
    }

    private YargleAndMultani(final YargleAndMultani card) {
        super(card);
    }

    @Override
    public YargleAndMultani copy() {
        return new YargleAndMultani(this);
    }
}
