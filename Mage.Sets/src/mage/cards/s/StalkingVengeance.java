
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class StalkingVengeance extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
    }

    public StalkingVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Whenever another creature you control dies, it deals damage equal to its power to target player.
        Ability ability = new DiesCreatureTriggeredAbility(new StalkingVengeanceDamageEffect(), false, filter, true);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    public StalkingVengeance(final StalkingVengeance card) {
        super(card);
    }

    @Override
    public StalkingVengeance copy() {
        return new StalkingVengeance(this);
    }
}

class StalkingVengeanceDamageEffect extends OneShotEffect {

    public StalkingVengeanceDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals damage equal to its power to target player or planeswalker";
    }

    public StalkingVengeanceDamageEffect(final StalkingVengeanceDamageEffect effect) {
        super(effect);
    }

    @Override
    public StalkingVengeanceDamageEffect copy() {
        return new StalkingVengeanceDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = (Permanent) game.getLastKnownInformation(this.getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        if (creature != null) {
            game.damagePlayerOrPlaneswalker(source.getFirstTarget(), creature.getPower().getValue(), creature.getId(), game, false, true);
            return true;
        }
        return false;
    }
}
