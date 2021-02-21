
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author ciaccona007
 */
public final class AerialGuide extends CardImpl {

    static final FilterAttackingCreature filter = new FilterAttackingCreature("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }
    
    public AerialGuide(UUID ownerId, CardSetInfo setInfo) {
        
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Aerial Guide attacks, another target attacking creature gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), false);
        ability.addTarget(new TargetAttackingCreature(1, 1, filter, false));
        addAbility(ability);
        
    }

    private AerialGuide(final AerialGuide card) {
        super(card);
    }

    @Override
    public AerialGuide copy() {
        return new AerialGuide(this);
    }
}
