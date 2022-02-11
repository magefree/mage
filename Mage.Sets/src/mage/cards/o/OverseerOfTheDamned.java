
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class OverseerOfTheDamned extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a nontoken creature an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(TokenPredicate.FALSE);
    }

    public OverseerOfTheDamned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Overseer of the Damned enters the battlefield, you may destroy target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // Whenever a nontoken creature an opponent controls dies, create a tapped 2/2 black Zombie creature token.
        Token zombie = new ZombieToken();
        zombie.setTokenType(2);
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(zombie, 1, true, false), false, filter));

    }

    private OverseerOfTheDamned(final OverseerOfTheDamned card) {
        super(card);
    }

    @Override
    public OverseerOfTheDamned copy() {
        return new OverseerOfTheDamned(this);
    }
}
