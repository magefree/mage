
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class TowerOfTheMagistrate extends CardImpl {

    public TowerOfTheMagistrate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {tap}: Target creature gains protection from artifacts until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(ProtectionAbility.from(CardType.ARTIFACT)), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TowerOfTheMagistrate(final TowerOfTheMagistrate card) {
        super(card);
    }

    @Override
    public TowerOfTheMagistrate copy() {
        return new TowerOfTheMagistrate(this);
    }
}
