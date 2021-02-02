
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 * @author Rystan
 */
public final class MemorialToUnity extends CardImpl {

    public MemorialToUnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Memorial to Uniity enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        this.addAbility(new GreenManaAbility());

        // {2}{G}, {T}, Sacrifice Memorial to Unity: Look at the top five cards of your library. You may reveal a creature card from among them and put it into your hand. Then put the rest on the bottom of your library in a random order.
        Effect effect = new LookLibraryAndPickControllerEffect(
                StaticValue.get(5), false, StaticValue.get(1), new FilterCreatureCard("a creature card"), false, true
        ).setBackInRandomOrder(true);

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private MemorialToUnity(final MemorialToUnity card) {
        super(card);
    }

    @Override
    public MemorialToUnity copy() {
        return new MemorialToUnity(this);
    }

}
