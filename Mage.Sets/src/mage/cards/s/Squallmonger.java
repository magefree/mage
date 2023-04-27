
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author fireshoes
 */
public final class Squallmonger extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");
    
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Squallmonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.MONGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}: Squallmonger deals 1 damage to each creature with flying and each player. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageAllEffect(1, filter), new ManaCostsImpl<>("{2}"));
        Effect effect = new DamagePlayersEffect(1);
        effect.setText("and each player");
        ability.addEffect(effect);
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private Squallmonger(final Squallmonger card) {
        super(card);
    }

    @Override
    public Squallmonger copy() {
        return new Squallmonger(this);
    }
}
