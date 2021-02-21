
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class MarkovCrusader extends CardImpl {

    private static final String rule = "{this} has haste as long as you control another Vampire";
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another Vampire");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public MarkovCrusader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Markov Crusader has haste as long as you control another Vampire.
        Effect effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(HasteAbility.getInstance()), new PermanentsOnTheBattlefieldCondition(filter), rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private MarkovCrusader(final MarkovCrusader card) {
        super(card);
    }

    @Override
    public MarkovCrusader copy() {
        return new MarkovCrusader(this);
    }
}
