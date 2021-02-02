
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SetTargetPointer;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.ThopterToken;

/**
 *
 * @author Plopman
 */
public final class ShardingSphinx extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("an artifact creature you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public ShardingSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever an artifact creature you control deals combat damage to a player, you may create a 1/1 blue Thopter artifact creature token with flying.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new CreateTokenEffect(new ThopterToken()),
                filter, true, SetTargetPointer.NONE, true));

    }

    private ShardingSphinx(final ShardingSphinx card) {
        super(card);
    }

    @Override
    public ShardingSphinx copy() {
        return new ShardingSphinx(this);
    }
}
