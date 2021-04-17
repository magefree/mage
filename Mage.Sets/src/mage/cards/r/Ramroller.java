
package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class Ramroller extends CardImpl {

    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    public Ramroller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Ramroller attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
        // Ramroller gets +2/+0 as long as you control another artifact.
        Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostSourceEffect(2, 0,
                Duration.WhileOnBattlefield), condition, "{this} gets +2/+0 as long as you control another artifact.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private Ramroller(final Ramroller card) {
        super(card);
    }

    @Override
    public Ramroller copy() {
        return new Ramroller(this);
    }
}
