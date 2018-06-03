
package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author fireshoes
 */
public final class LilianasMastery extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombie creatures");

    static {
        filter.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    public LilianasMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // Zombies creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));

        // When Liliana's Army enters the battlefield, create two 2/2 black Zombie creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ZombieToken(), 2)));
    }

    public LilianasMastery(final LilianasMastery card) {
        super(card);
    }

    @Override
    public LilianasMastery copy() {
        return new LilianasMastery(this);
    }
}
