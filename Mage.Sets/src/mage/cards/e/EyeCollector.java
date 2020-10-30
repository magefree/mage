package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EyeCollector extends CardImpl {

    public EyeCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Eye Collector deals combat damage to a player, each player puts the top card of their library into their graveyard.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new MillCardsEachPlayerEffect(1, TargetController.ANY), false));
    }

    private EyeCollector(final EyeCollector card) {
        super(card);
    }

    @Override
    public EyeCollector copy() {
        return new EyeCollector(this);
    }
}
