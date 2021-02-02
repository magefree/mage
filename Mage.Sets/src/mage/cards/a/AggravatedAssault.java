
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AddCombatAndMainPhaseEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class AggravatedAssault extends CardImpl {

    public AggravatedAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");


        // {3}{R}{R}: Untap all creatures you control. After this main phase, there is an additional combat phase followed by an additional main phase. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), "Untap all creatures you control"), new ManaCostsImpl<>("{3}{R}{R}"));
        ability.addEffect(new AddCombatAndMainPhaseEffect());
        this.addAbility(ability);
    }

    private AggravatedAssault(final AggravatedAssault card) {
        super(card);
    }

    @Override
    public AggravatedAssault copy() {
        return new AggravatedAssault(this);
    }
}
