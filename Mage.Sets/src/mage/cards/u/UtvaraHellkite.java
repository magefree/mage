
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.UtvaraHellkiteDragonToken;

/**
 *
 * @author LevelX2
 */
public final class UtvaraHellkite extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Dragon you control");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public UtvaraHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a Dragon you control attacks, create a 6/6 red Dragon creature token with flying.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(new CreateTokenEffect(new UtvaraHellkiteDragonToken()), false, filter));
    }

    private UtvaraHellkite(final UtvaraHellkite card) {
        super(card);
    }

    @Override
    public UtvaraHellkite copy() {
        return new UtvaraHellkite(this);
    }
}
