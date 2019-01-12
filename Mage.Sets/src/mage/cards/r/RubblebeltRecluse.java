package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RubblebeltRecluse extends CardImpl {

    public RubblebeltRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Rubblebelt Recluse attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private RubblebeltRecluse(final RubblebeltRecluse card) {
        super(card);
    }

    @Override
    public RubblebeltRecluse copy() {
        return new RubblebeltRecluse(this);
    }
}
