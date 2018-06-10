
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author emerald000
 */
public final class DreamProwler extends CardImpl {

    public DreamProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Dream Prowler can't be blocked as long as it's attacking alone.
        Effect effect = new ConditionalRestrictionEffect(new CantBeBlockedSourceEffect(), new PermanentsOnTheBattlefieldCondition(new FilterAttackingCreature(), ComparisonType.FEWER_THAN, 2));
        effect.setText("{this} can't be blocked as long as it's attacking alone");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public DreamProwler(final DreamProwler card) {
        super(card);
    }

    @Override
    public DreamProwler copy() {
        return new DreamProwler(this);
    }
}
