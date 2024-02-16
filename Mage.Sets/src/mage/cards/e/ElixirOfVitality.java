
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Derpthemeus
 */
public final class ElixirOfVitality extends CardImpl {

    public ElixirOfVitality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Elixir of Vitality enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}, Sacrifice Elixir of Vitality: You gain 4 life.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(4), new TapSourceCost());
        ability1.addCost(new SacrificeSourceCost());
        this.addAbility(ability1);
        // {8}, {tap}, Sacrifice Elixir of Vitality: You gain 8 life.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(8), new ManaCostsImpl<>("{8}"));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new SacrificeSourceCost());
        this.addAbility(ability2);
    }

    private ElixirOfVitality(final ElixirOfVitality card) {
        super(card);
    }

    @Override
    public ElixirOfVitality copy() {
        return new ElixirOfVitality(this);
    }
}
