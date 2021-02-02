
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.SquirrelToken;

/**
 *
 * @author Plopman
 */
public final class DerangedHermit extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Squirrel creatures");

    static {
        filter.add(SubType.SQUIRREL.getPredicate());
    }

    public DerangedHermit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Echo {3}{G}{G}
        this.addAbility(new EchoAbility("{3}{G}{G}"));
        // When Deranged Hermit enters the battlefield, create four 1/1 green Squirrel creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SquirrelToken(), 4)));
        // Squirrel creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));

    }

    private DerangedHermit(final DerangedHermit card) {
        super(card);
    }

    @Override
    public DerangedHermit copy() {
        return new DerangedHermit(this);
    }
}
