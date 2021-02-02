package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author NinthWorld
 */
public final class GerrerasRevolutionary extends CardImpl {

    public GerrerasRevolutionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        
        this.subtype.add(SubType.BARABEL);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Gerrera's Revolutionary attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private GerrerasRevolutionary(final GerrerasRevolutionary card) {
        super(card);
    }

    @Override
    public GerrerasRevolutionary copy() {
        return new GerrerasRevolutionary(this);
    }
}
