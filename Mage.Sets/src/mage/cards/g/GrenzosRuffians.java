package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.DamageEachOtherOpponentThatMuchEffect;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrenzosRuffians extends CardImpl {

    public GrenzosRuffians(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Melee
        this.addAbility(new MeleeAbility());

        // Whenever Grenzo's Ruffians deals combat damage to a opponent, it deals that much damage to each other opponent.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(
                new DamageEachOtherOpponentThatMuchEffect(), false, true, true
        ));
    }

    private GrenzosRuffians(final GrenzosRuffians card) {
        super(card);
    }

    @Override
    public GrenzosRuffians copy() {
        return new GrenzosRuffians(this);
    }
}
