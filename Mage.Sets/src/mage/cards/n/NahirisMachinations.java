
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterBlockingCreature;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class NahirisMachinations extends CardImpl {

    public NahirisMachinations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // At the beginning of combat on your turn, target creature you control gains indestructible until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // {1}{R}: Nahiri's Machinations deals 1 damage to target blocking creature.
        ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new ManaCostsImpl<>("{1}{R}"));
        ability.addTarget(new TargetPermanent(new FilterBlockingCreature("blocking creature")));
        this.addAbility(ability);
    }

    private NahirisMachinations(final NahirisMachinations card) {
        super(card);
    }

    @Override
    public NahirisMachinations copy() {
        return new NahirisMachinations(this);
    }
}
