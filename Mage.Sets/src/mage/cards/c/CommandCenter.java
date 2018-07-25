package mage.cards.c;

import java.util.UUID;

import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author NinthWorld
 */
public final class CommandCenter extends CardImpl {

    public CommandCenter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Command Center enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Command Center enters the battlefield, add {C} to your mana pool.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddManaToManaPoolSourceControllerEffect(Mana.ColorlessMana(1))));

        // {T}: Add {R} or {W} to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(1), new TapSourceCost()));
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(1), new TapSourceCost()));
    }

    public CommandCenter(final CommandCenter card) {
        super(card);
    }

    @Override
    public CommandCenter copy() {
        return new CommandCenter(this);
    }
}
