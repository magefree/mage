package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Sabotender extends CardImpl {

    public Sabotender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.PLANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Landfall -- Whenever a land you control enters, this creature deals 1 damage to each opponent.
        this.addAbility(new LandfallAbility(new DamagePlayersEffect(1, TargetController.OPPONENT)));
    }

    private Sabotender(final Sabotender card) {
        super(card);
    }

    @Override
    public Sabotender copy() {
        return new Sabotender(this);
    }
}
