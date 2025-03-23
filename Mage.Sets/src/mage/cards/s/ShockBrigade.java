package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.MobilizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShockBrigade extends CardImpl {

    public ShockBrigade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Mobilize 1
        this.addAbility(new MobilizeAbility(1));
    }

    private ShockBrigade(final ShockBrigade card) {
        super(card);
    }

    @Override
    public ShockBrigade copy() {
        return new ShockBrigade(this);
    }
}
