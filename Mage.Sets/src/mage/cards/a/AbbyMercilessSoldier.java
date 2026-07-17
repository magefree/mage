package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.ManaSpentToCastCount;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.EntersBattlefieldUnderControlOfOpponentOfChoiceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PartnerVariantType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.CordycepsInfectedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbbyMercilessSoldier extends CardImpl {

    public AbbyMercilessSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When you cast this spell, create a number of 1/1 black Fungus Zombie creature tokens named Cordyceps Infected equal to the amount of mana spent to cast it.
        this.addAbility(new CastSourceTriggeredAbility(
                new CreateTokenEffect(new CordycepsInfectedToken(), ManaSpentToCastCount.instance)
                        .setText("create a number of 1/1 black Fungus Zombie creature tokens " +
                                "named Cordyceps Infected equal to the amount of mana spent to cast it")
        ));

        // Abby enters under the control of an opponent of your choice.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldUnderControlOfOpponentOfChoiceEffect()));

        // Partner--Survivors
        this.addAbility(PartnerVariantType.SURVIVORS.makeAbility());
    }

    private AbbyMercilessSoldier(final AbbyMercilessSoldier card) {
        super(card);
    }

    @Override
    public AbbyMercilessSoldier copy() {
        return new AbbyMercilessSoldier(this);
    }
}
