
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class AbyssalHunter extends CardImpl {

    public AbyssalHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}, {tap}: Tap target creature. Abyssal Hunter deals damage equal to Abyssal Hunter's power to that creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new ManaCostsImpl<>("{B}"));
        Effect effect = new DamageTargetEffect(new SourcePermanentPowerCount());
        effect.setText("{this} deals damage equal to {this}'s power to that creature.");
        ability.addEffect(effect);
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AbyssalHunter(final AbyssalHunter card) {
        super(card);
    }

    @Override
    public AbyssalHunter copy() {
        return new AbyssalHunter(this);
    }
}
