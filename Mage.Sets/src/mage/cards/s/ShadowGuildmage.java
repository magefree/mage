
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class ShadowGuildmage extends CardImpl {

    public ShadowGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {U}, {tap}: Put target creature you control on top of its owner's library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutOnLibraryTargetEffect(true), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
        
        // {R}, {tap}: Shadow Guildmage deals 1 damage to any target and 1 damage to you.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new DamageControllerEffect(1));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ShadowGuildmage(final ShadowGuildmage card) {
        super(card);
    }

    @Override
    public ShadowGuildmage copy() {
        return new ShadowGuildmage(this);
    }
}