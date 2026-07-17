package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.DamageEachOtherOpponentThatMuchEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AmarantCoral extends CardImpl {

    public AmarantCoral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Amarant Coral attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // No Mercy -- Whenever Amarant Coral deals combat damage to an opponent, it deals that much damage to each other opponent.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(
                new DamageEachOtherOpponentThatMuchEffect(), false, true, true
        ).withFlavorWord("No Mercy"));
    }

    private AmarantCoral(final AmarantCoral card) {
        super(card);
    }

    @Override
    public AmarantCoral copy() {
        return new AmarantCoral(this);
    }
}
