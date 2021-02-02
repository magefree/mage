package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AlmsBeast extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.EITHER);
    }

    public AlmsBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Creatures blocking or blocked by Alms Beast have lifelink.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                LifelinkAbility.getInstance(), Duration.WhileOnBattlefield,
                filter, "Creatures blocking or blocked by {this} have lifelink"
        )));
    }

    private AlmsBeast(final AlmsBeast card) {
        super(card);
    }

    @Override
    public AlmsBeast copy() {
        return new AlmsBeast(this);
    }
}
