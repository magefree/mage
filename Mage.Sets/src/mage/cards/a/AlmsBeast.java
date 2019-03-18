
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;

/**
 *
 * @author LevelX2
 */
public final class AlmsBeast extends CardImpl {

    public AlmsBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{B}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Creatures blocking or blocked by Alms Beast have lifelink.
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(Predicates.or(new BlockedByIdPredicate(this.getId()),
                                 new BlockingAttackerIdPredicate(this.getId())));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter,
                "Creatures blocking or blocked by {this} have lifelink")));
    }

    public AlmsBeast(final AlmsBeast card) {
        super(card);
    }

    @Override
    public AlmsBeast copy() {
        return new AlmsBeast(this);
    }
}
