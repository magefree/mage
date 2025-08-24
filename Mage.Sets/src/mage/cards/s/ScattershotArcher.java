
package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class ScattershotArcher extends CardImpl {

    public ScattershotArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}: Scattershot Archer deals 1 damage to each creature with flying.
        this.addAbility(new SimpleActivatedAbility(new DamageAllEffect(1, StaticFilters.FILTER_CREATURE_FLYING), new TapSourceCost()));
    }

    private ScattershotArcher(final ScattershotArcher card) {
        super(card);
    }

    @Override
    public ScattershotArcher copy() {
        return new ScattershotArcher(this);
    }
}
