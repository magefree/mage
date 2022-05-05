package mage.cards.j;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
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

    private JackInTheMox(final JackInTheMox card) {
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
        staticText = "roll a six-sided die. This ability has the indicated effect."
                + "<br>1 - Sacrifice {this} and you lose 5 life."
                + "<br>2 - Add {W}."
                + "<br>3 - Add {U}."
                + "<br>4 - Add {B}."
                + "<br>5 - Add {R}."
                + "<br>6 - Add {G}.";
    }

    JackInTheMoxManaEffect(final JackInTheMoxManaEffect effect) {
        super(effect);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        return new ArrayList<>();
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(outcome, source, game, 6);
            switch (amount) {
                case 1:
                    permanent.sacrifice(source, game);
                    controller.loseLife(5, game, source, false);
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
        }
        return mana;
    }

    @Override
    public JackInTheMoxManaEffect copy() {
        return new JackInTheMoxManaEffect(this);
    }
}
