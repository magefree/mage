package mage.cards.r;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.mana.DoUnlessAnyPlayerPaysManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ManaChoice;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author jerekwilson
 */
public final class RhysticCave extends CardImpl {

    public RhysticCave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Choose a color. Add one mana of that color unless any player pays {1}. Activate this ability only any time you could cast an instant.
        this.addAbility(new RhysticCaveManaAbility());
    }

    private RhysticCave(final RhysticCave card) {
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
        return super.getRule() + " Activate only as an instant.";
    }
}

class RhysticCaveManaEffect extends ManaEffect {

    public RhysticCaveManaEffect() {
        super();
        this.staticText = "Choose a color. Add one mana of that color";
    }

    public RhysticCaveManaEffect(final RhysticCaveManaEffect effect) {
        super(effect);
    }

    // "The card’s ability has errata so you can’t activate the ability during casting of a spell or activating of an ability."
    // (2004-10-04)
    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        // Returning no mana instead of 1 mana of any color so that it won't highlight spells that need it as castable.
        // This land must be tapped for mana as an instant and the mana must be floated.
        netMana.add(new Mana());
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            Player controller = getPlayer(game, source);
            return ManaChoice.chooseAnyColor(controller, game, 1);
        }
        return new Mana();
    }

    @Override
    public Effect copy() {
        return new RhysticCaveManaEffect(this);
    }
}
