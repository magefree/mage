package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class KlingonStrikeForce extends CardImpl {

    public KlingonStrikeForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When this creature dies, it deals 2 damage to each opponent.
        this.addAbility(new DiesSourceTriggeredAbility(new DamagePlayersEffect(
            2, TargetController.OPPONENT, "it"
        )));
    }

    private KlingonStrikeForce(final KlingonStrikeForce card) {
        super(card);
    }

    @Override
    public KlingonStrikeForce copy() {
        return new KlingonStrikeForce(this);
    }
}
