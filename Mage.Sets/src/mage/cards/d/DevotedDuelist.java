package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.FlurryAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DevotedDuelist extends CardImpl {

    public DevotedDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Flurry -- Whenever you cast your second spell each turn, this creature deals 1 damage to each opponent.
        this.addAbility(new FlurryAbility(new DamagePlayersEffect(1, TargetController.OPPONENT)));
    }

    private DevotedDuelist(final DevotedDuelist card) {
        super(card);
    }

    @Override
    public DevotedDuelist copy() {
        return new DevotedDuelist(this);
    }
}
