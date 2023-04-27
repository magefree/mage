package mage.cards.d;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;

/**
 * @author jeffwadsworth
 */
public final class DoublingCube extends CardImpl {

    public DoublingCube(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {3}, {T}: Double the amount of each type of mana in your mana pool.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new DoublingCubeEffect(), new ManaCostsImpl<>("{3}"))
                .setPoolDependant(true);        
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private DoublingCube(final DoublingCube card) {
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
    public List<Mana> getNetMana(Game game, Mana possibleManaInPool, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        netMana.add(new Mana( // remove possible mana conditions
                possibleManaInPool.getWhite(), possibleManaInPool.getBlue(), possibleManaInPool.getBlack(), possibleManaInPool.getRed(),
                possibleManaInPool.getGreen(),
                0, // Generic may not be included
                possibleManaInPool.getAny(), 
                possibleManaInPool.getColorless())
        );        
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
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
                return new Mana(whiteMana, blueMana, blackMana, redMana, greenMana, 0, 0, colorlessMana);
            }
        }
        return new Mana();
    }

    @Override
    public DoublingCubeEffect copy() {
        return new DoublingCubeEffect(this);
    }

}
