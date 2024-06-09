package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class NinThePainArtist extends CardImpl {

    public NinThePainArtist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{U}{R}, {tap}: Nin, the Pain Artist deals X damage to target creature. That creature's controller draws X cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(ManacostVariableValue.REGULAR), new ManaCostsImpl<>("{X}{U}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new DrawCardTargetControllerEffect(ManacostVariableValue.REGULAR)
                .setText("that creature's controller draws X cards"));
        this.addAbility(ability);
    }

    private NinThePainArtist(final NinThePainArtist card) {
        super(card);
    }

    @Override
    public NinThePainArtist copy() {
        return new NinThePainArtist(this);
    }
}
