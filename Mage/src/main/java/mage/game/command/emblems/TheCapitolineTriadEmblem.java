package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.*;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/*
 * @author grimreap124
 */
public final class TheCapitolineTriadEmblem extends Emblem {

    public TheCapitolineTriadEmblem() {
        super("Emblem Capitoline Triad");
        // You get an emblem with "Creatures you control have base power and toughness 9/9."
        Ability ability = new SimpleStaticAbility(Zone.COMMAND,
                new SetBasePowerToughnessAllEffect(9, 9, Duration.EndOfGame, StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED).setText("Creatures you control have base power and toughness 9/9."));
        getAbilities().add(ability);
    }

    private TheCapitolineTriadEmblem(final TheCapitolineTriadEmblem card) {
        super(card);
    }

    @Override
    public TheCapitolineTriadEmblem copy() {
        return new TheCapitolineTriadEmblem(this);
    }
}
