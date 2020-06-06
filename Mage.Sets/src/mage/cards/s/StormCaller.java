package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormCaller extends CardImpl {

    public StormCaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Storm Caller enters the battlefield, it deals 2 damage to each opponent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DamagePlayersEffect(2, TargetController.OPPONENT, "it")
        ));
    }

    private StormCaller(final StormCaller card) {
        super(card);
    }

    @Override
    public StormCaller copy() {
        return new StormCaller(this);
    }
}
