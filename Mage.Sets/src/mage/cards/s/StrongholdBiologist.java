
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureSpell;
import mage.target.TargetSpell;

/**
 *
 * @author TheElk801
 */
public final class StrongholdBiologist extends CardImpl {

    public StrongholdBiologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {U}{U}, {tap}, Discard a card: Counter target creature spell.
        Ability ability = new SimpleActivatedAbility(new CounterTargetEffect(), new ManaCostsImpl<>("{U}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetSpell(new FilterCreatureSpell()));
        this.addAbility(ability);
    }

    private StrongholdBiologist(final StrongholdBiologist card) {
        super(card);
    }

    @Override
    public StrongholdBiologist copy() {
        return new StrongholdBiologist(this);
    }
}
