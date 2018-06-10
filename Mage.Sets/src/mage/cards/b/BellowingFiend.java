
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LoneFox

 */
public final class BellowingFiend extends CardImpl {

    public BellowingFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Bellowing Fiend deals damage to a creature, Bellowing Fiend deals 3 damage to that creature's controller and 3 damage to you.
        Ability ability = new DealsDamageToACreatureTriggeredAbility(new BellowingFiendEffect(), false, false, true);
        Effect effect = new DamageControllerEffect(3);
        effect.setText("and 3 damage to you");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public BellowingFiend(final BellowingFiend card) {
        super(card);
    }

    @Override
    public BellowingFiend copy() {
        return new BellowingFiend(this);
    }
}

class BellowingFiendEffect extends OneShotEffect {

    public BellowingFiendEffect() {
        super(Outcome.Detriment);
        this.staticText = "{this} deals 3 damage to that creature's controller";                                                                                          }

    public BellowingFiendEffect(final BellowingFiendEffect effect) {
        super(effect);
    }

    @Override
    public BellowingFiendEffect copy() {
        return new BellowingFiendEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        Permanent damagedCreature = game.getPermanentOrLKIBattlefield(targetPointer.getFirst(game, source));
        if(damagedCreature != null) {
            Player controller = game.getPlayer(damagedCreature.getControllerId());
            if(controller != null) {
                controller.damage(3, source.getSourceId(), game, false, true);                                                                                                       applied = true;
            }
        }
        return applied;
    }
}
