
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author Wehk
 */
public final class SoulswornJury extends CardImpl {

    public SoulswornJury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {1}{U}, Sacrifice Soulsworn Jury: Counter target creature spell.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SoulswornJury(final SoulswornJury card) {
        super(card);
    }

    @Override
    public SoulswornJury copy() {
        return new SoulswornJury(this);
    }
}
