
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceAttackingAloneCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

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
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(),
                SourceAttackingAloneCondition.instance,
                "{this} can't be blocked as long as it's attacking alone"
        )));
    }

    private DreamProwler(final DreamProwler card) {
        super(card);
    }

    @Override
    public DreamProwler copy() {
        return new DreamProwler(this);
    }
}
