
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureAttackingYou;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 *
 */
public final class SoulSnare extends CardImpl {

    public SoulSnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // {W}, Sacrifice Soul Snare: Exile target creature that's attacking you or a planeswalker you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ExileTargetEffect(), new ManaCostsImpl<>("{W}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent(new FilterCreatureAttackingYou(true)));
        this.addAbility(ability);
    }

    private SoulSnare(final SoulSnare card) {
        super(card);
    }

    @Override
    public SoulSnare copy() {
        return new SoulSnare(this);
    }
}
