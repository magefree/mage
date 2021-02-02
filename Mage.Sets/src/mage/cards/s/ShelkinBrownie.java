
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.BandsWithOtherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class ShelkinBrownie extends CardImpl {

    public ShelkinBrownie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.OUPHE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Target creature loses all "bands with other" abilities until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseAbilityTargetEffect(new BandsWithOtherAbility(), Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ShelkinBrownie(final ShelkinBrownie card) {
        super(card);
    }

    @Override
    public ShelkinBrownie copy() {
        return new ShelkinBrownie(this);
    }
}
