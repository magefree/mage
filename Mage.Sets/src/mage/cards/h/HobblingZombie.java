package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ZombieDecayedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HobblingZombie extends CardImpl {

    public HobblingZombie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Hobbling Zombie dies, create a 2/2 black Zombie creature with decayed.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new ZombieDecayedToken())));
    }

    private HobblingZombie(final HobblingZombie card) {
        super(card);
    }

    @Override
    public HobblingZombie copy() {
        return new HobblingZombie(this);
    }
}
