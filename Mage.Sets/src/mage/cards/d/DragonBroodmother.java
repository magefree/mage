package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.DragonBroodmotherDragonToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DragonBroodmother extends CardImpl {

    public DragonBroodmother(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{R}{G}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each upkeep, create a 1/1 red and green Dragon creature token with flying and devour 2. (As the token enters the battlefield, you may sacrifice any number of creatures. It enters the battlefield with twice that many +1/+1 counters on it.)
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenEffect(new DragonBroodmotherDragonToken()),
                TargetController.EACH_PLAYER, false
        ));
    }

    private DragonBroodmother(final DragonBroodmother card) {
        super(card);
    }

    @Override
    public DragonBroodmother copy() {
        return new DragonBroodmother(this);
    }
}
