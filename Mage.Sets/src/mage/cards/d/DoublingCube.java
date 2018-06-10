
package mage.cards.d;

import java.util.UUID;
import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class DoublingCube extends CardImpl {

    public DoublingCube(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {3}, {T}: Double the amount of each type of mana in your mana pool.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new DoublingCubeEffect(), new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public DoublingCube(final DoublingCube card) {
        super(card);
    }

    @Override
    public DoublingCube copy() {
        return new DoublingCube(this);
    }
}

class DoublingCubeEffect extends ManaEffect {

    DoublingCubeEffect() {
        super();
        staticText = "Double the amount of each type of unspent mana you have";
    }

    DoublingCubeEffect(final DoublingCubeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        checkToFirePossibleEvents(getMana(game, source), game, source);
        controller.getManaPool().addMana(getMana(game, source), game, source);
        return true;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return null;
        }
        ManaPool pool = controller.getManaPool();
        int blackMana = pool.getBlack();
        int whiteMana = pool.getWhite();
        int blueMana = pool.getBlue();
        int greenMana = pool.getGreen();
        int redMana = pool.getRed();
        int colorlessMana = pool.getColorless();

        for (ConditionalMana conditionalMana : pool.getConditionalMana()) {
            blackMana += conditionalMana.getBlack();
            whiteMana += conditionalMana.getWhite();
            blueMana += conditionalMana.getBlue();
            greenMana += conditionalMana.getGreen();
            redMana += conditionalMana.getRed();
            colorlessMana += conditionalMana.getColorless();
        }
        return new Mana(redMana, greenMana, blueMana, whiteMana, blackMana, 0, 0, colorlessMana);
    }

    @Override
    public DoublingCubeEffect copy() {
        return new DoublingCubeEffect(this);
    }

}
