package mage.abilities.keyword;

import mage.MageObject;
import mage.filter.FilterCard;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class ProtectionFromEverythingAbility extends ProtectionAbility {

    private static final FilterCard filter = new FilterCard("everything");

    public ProtectionFromEverythingAbility() {
        super(filter);
    }

    private ProtectionFromEverythingAbility(final ProtectionFromEverythingAbility ability) {
        super(ability);
    }

    @Override
    public ProtectionFromEverythingAbility copy() {
        return new ProtectionFromEverythingAbility(this);
    }

    //     2/1/2009: "Protection from everything" means the following: Progenitus can't be blocked,
    //               Progenitus can't be enchanted or equipped, Progenitus can't be the target of
    //               spells or abilities, and all damage that would be dealt to Progenitus is prevented.
    //     2/1/2009: Progenitus can still be affected by effects that don't target it or deal damage
    //               to it (such as Day of Judgment).
    @Override
    public boolean canTarget(MageObject source, Game game) {
        return false;
    }
}
