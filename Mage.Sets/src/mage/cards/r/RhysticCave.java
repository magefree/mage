
package mage.cards.r;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.mana.DoUnlessAnyPlayerPaysManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jerekwilson
 */
public final class RhysticCave extends CardImpl {

    public RhysticCave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Choose a color. Add one mana of that color unless any player pays {1}. Activate this ability only any time you could cast an instant.
        this.addAbility(new RhysticCaveManaAbility());
    }

    public RhysticCave(final RhysticCave card) {
        super(card);
    }

    @Override
    public RhysticCave copy() {
        return new RhysticCave(this);
    }
}

class RhysticCaveManaAbility extends ActivatedManaAbilityImpl {

    public RhysticCaveManaAbility() {
        super(Zone.BATTLEFIELD, new DoUnlessAnyPlayerPaysManaEffect(new RhysticCaveManaEffect(), new GenericManaCost(1), "Pay {1} to prevent mana adding from {this}."), new TapSourceCost());
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 0, 1, 0));
        this.setUndoPossible(false);
    }

    public RhysticCaveManaAbility(Zone zone, Mana mana, Cost cost) {
        super(zone, new BasicManaEffect(mana), cost);

    }

    public RhysticCaveManaAbility(final RhysticCaveManaAbility ability) {
        super(ability);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);
        if (player != null && !player.isInPayManaMode()) {
            return super.canActivate(playerId, game);
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public RhysticCaveManaAbility copy() {
        return new RhysticCaveManaAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate this ability only any time you could cast an instant.";
    }
}

class RhysticCaveManaEffect extends ManaEffect {

    public RhysticCaveManaEffect() {
        super();
        this.staticText = "Choose a color. Add one mana of that color ";
    }

    public RhysticCaveManaEffect(final RhysticCaveManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ChoiceColor choice = new ChoiceColor(true);
        if (controller != null && controller.choose(outcome, choice, game)) {
            Mana chosenMana = new Mana();
            switch (choice.getColor().toString()) {
                case "R":
                    chosenMana.setRed(1);
                    break;
                case "U":
                    chosenMana.setBlue(1);
                    break;
                case "W":
                    chosenMana.setWhite(1);
                    break;
                case "B":
                    chosenMana.setBlack(1);
                    break;
                case "G":
                    chosenMana.setGreen(1);
                    break;
            }
            return chosenMana;
        }
        return null;
    }

    @Override
    public Effect copy() {
        return new RhysticCaveManaEffect(this);
    }
}
