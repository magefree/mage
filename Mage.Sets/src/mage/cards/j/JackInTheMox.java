
package mage.cards.j;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class JackInTheMox extends CardImpl {

    public JackInTheMox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // {T}: Roll a six-sided die. This ability has the indicated effect.
        // 1 - Sacrifice Jack-in-the-Mox and you lose 5 life.
        // 2 - Add {W}.
        // 3 - Add {U}.
        // 4 - Add {B}.
        // 5 - Add {R}.
        // 6 - Add {G}.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, new JackInTheMoxManaEffect(), new TapSourceCost());
        ability.setUndoPossible(false);
        this.addAbility(ability);
    }

    public JackInTheMox(final JackInTheMox card) {
        super(card);
    }

    @Override
    public JackInTheMox copy() {
        return new JackInTheMox(this);
    }
}

class JackInTheMoxManaEffect extends ManaEffect {

    JackInTheMoxManaEffect() {
        super();
        staticText = "Roll a six-sided die for {this}. On a 1, sacrifice {this} and lose 5 life. Otherwise, {this} has one of the following effects. Treat this ability as a mana source."
                + "<br/>2 Add {W}.\n"
                + "<br/>3 Add {U}.\n"
                + "<br/>4 Add {B}.\n"
                + "<br/>5 Add {R}.\n"
                + "<br/>6 Add {G}.";
    }

    JackInTheMoxManaEffect(final JackInTheMoxManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(game, 6);
            Mana mana = new Mana();
            switch (amount) {
                case 1:
                    permanent.sacrifice(source.getSourceId(), game);
                    controller.loseLife(5, game, false);
                    break;
                case 2:
                    mana.add(Mana.WhiteMana(1));
                    break;
                case 3:
                    mana.add(Mana.BlueMana(1));
                    break;
                case 4:
                    mana.add(Mana.BlackMana(1));
                    break;
                case 5:
                    mana.add(Mana.RedMana(1));
                    break;
                case 6:
                    mana.add(Mana.GreenMana(1));
                    break;
                default:
                    break;
            }
            return mana;
        }
        return null;
    }

    @Override
    public JackInTheMoxManaEffect copy() {
        return new JackInTheMoxManaEffect(this);
    }
}
