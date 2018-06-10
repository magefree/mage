
package mage.cards.x;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author LevelX2
 */
public final class XathridNecromancer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Human creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.HUMAN));
    }

    public XathridNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Xathrid Necromancer or another Human creature you control dies, create a tapped 2/2 black Zombie creature token.
        Effect effect = new CreateTokenEffect(new ZombieToken(), 1, true, false);
        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(effect, false, filter);
        this.addAbility(ability);

    }

    public XathridNecromancer(final XathridNecromancer card) {
        super(card);
    }

    @Override
    public XathridNecromancer copy() {
        return new XathridNecromancer(this);
    }
}
