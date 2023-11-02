package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BecomesTargetSourceFirstTimeTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author LevelX2, xenohedron
 */
public final class ShimmeringGlasskite extends CardImpl {

    public ShimmeringGlasskite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Shimmering Glasskite becomes the target of a spell or ability for the first time each turn, counter that spell or ability.
        this.addAbility(new BecomesTargetSourceFirstTimeTriggeredAbility(
                new CounterTargetEffect().setText("counter that spell or ability"),
                StaticFilters.FILTER_SPELL_OR_ABILITY_A, SetTargetPointer.SPELL, false
        ));

    }

    private ShimmeringGlasskite(final ShimmeringGlasskite card) {
        super(card);
    }

    @Override
    public ShimmeringGlasskite copy() {
        return new ShimmeringGlasskite(this);
    }
}
