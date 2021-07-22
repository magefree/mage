
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox

 */
public final class SmolderingTar extends CardImpl {

    public SmolderingTar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}{R}");

        // At the beginning of your upkeep, target player loses 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new LoseLifeTargetEffect(1), TargetController.YOU, false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // Sacrifice Smoldering Tar: Smoldering Tar deals 4 damage to target creature. Activate this ability only any time you could cast a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(4, "it"), new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SmolderingTar(final SmolderingTar card) {
        super(card);
    }

    @Override
    public SmolderingTar copy() {
        return new SmolderingTar(this);
    }
}
