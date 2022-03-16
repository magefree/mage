package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DongZhouTheTyrant extends CardImpl {

    public DongZhouTheTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Dong Zhou, the Tyrant enters the battlefield, target creature an opponent controls deals damage equal to its power to that player.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DongZhouTheTyrantEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private DongZhouTheTyrant(final DongZhouTheTyrant card) {
        super(card);
    }

    @Override
    public DongZhouTheTyrant copy() {
        return new DongZhouTheTyrant(this);
    }
}

class DongZhouTheTyrantEffect extends OneShotEffect {

    public DongZhouTheTyrantEffect() {
        super(Outcome.Damage);
        staticText = "target creature an opponent controls deals damage equal to its power to that player";
    }

    public DongZhouTheTyrantEffect(final DongZhouTheTyrantEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            int amount = creature.getPower().getValue();
            Player controller = game.getPlayer(creature.getControllerId());
            if (controller != null) {
                controller.damage(amount, creature.getId(), source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public DongZhouTheTyrantEffect copy() {
        return new DongZhouTheTyrantEffect(this);
    }

}