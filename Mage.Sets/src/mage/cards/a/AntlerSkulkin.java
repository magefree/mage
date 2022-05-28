
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth

 */
public final class AntlerSkulkin extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white creature");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public AntlerSkulkin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.SCARECROW);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}: Target white creature gains persist until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(new PersistAbility(), Duration.EndOfTurn), new ManaCostsImpl<>("{2}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        
    }

    private AntlerSkulkin(final AntlerSkulkin card) {
        super(card);
    }

    @Override
    public AntlerSkulkin copy() {
        return new AntlerSkulkin(this);
    }
}
