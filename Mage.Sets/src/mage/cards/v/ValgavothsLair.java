package mage.cards.v;

import mage.abilities.common.EntersBattlefieldTappedAsItEntersChooseColorAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValgavothsLair extends CardImpl {

    public ValgavothsLair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.LAND}, "");

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Valgavoth's Lair enters tapped. As it enters, choose a color.
        this.addAbility(new EntersBattlefieldTappedAsItEntersChooseColorAbility());

        // {T}: Add one mana of the chosen color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));
    }

    private ValgavothsLair(final ValgavothsLair card) {
        super(card);
    }

    @Override
    public ValgavothsLair copy() {
        return new ValgavothsLair(this);
    }
}
