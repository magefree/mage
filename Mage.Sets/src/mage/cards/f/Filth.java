
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Backfir3
 */
public final class Filth extends CardImpl {

    private static final String ruleText = "As long as Filth is in your graveyard and you control a Swamp, creatures you control have swampwalk";

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Swamp");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public Filth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.INCARNATION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());

        // As long as Filth is in your graveyard and you control a Swamp, creatures you control have swampwalk.
        ContinuousEffect effect = new GainAbilityControlledEffect(new SwampwalkAbility(),
                Duration.WhileOnBattlefield, new FilterCreaturePermanent());
        ConditionalContinuousEffect filthEffect = new ConditionalContinuousEffect(effect,
                new PermanentsOnTheBattlefieldCondition(filter), ruleText);
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD, filthEffect));
    }

    public Filth(final Filth card) {
        super(card);
    }

    @Override
    public Filth copy() {
        return new Filth(this);
    }
}
