package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.OneOrMoreDiceRolledTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author weirddan455
 */
public final class BrazenDwarf extends CardImpl {

    public BrazenDwarf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you roll one or more dice, Brazen Dwarf deals 1 damage to each opponent.
        this.addAbility(new OneOrMoreDiceRolledTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT)));
    }

    private BrazenDwarf(final BrazenDwarf card) {
        super(card);
    }

    @Override
    public BrazenDwarf copy() {
        return new BrazenDwarf(this);
    }
}
