
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author tomd1990
 */
public final class EnslavedDwarf extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creature");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public EnslavedDwarf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}, Sacrifice Enslaved Dwarf: Target black creature gets +1/+0 and gains first strike until end of turn.
        Effect effect = new BoostTargetEffect(1,0,Duration.EndOfTurn);
        effect.setText("Target black creature gets +1/+0");
        Ability ability = new SimpleActivatedAbility(effect, new ManaCostsImpl<>("{R}"));
        ability.addCost(new SacrificeSourceCost());
        effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains first strike until end of turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private EnslavedDwarf(final EnslavedDwarf card) {
        super(card);
    }

    @Override
    public EnslavedDwarf copy() {
        return new EnslavedDwarf(this);
    }
}
