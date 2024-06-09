package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class HorobiDeathsWail extends CardImpl {

    public HorobiDeathsWail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature becomes the target of a spell or ability, destroy that creature.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(new DestroyTargetEffect(), StaticFilters.FILTER_PERMANENT_A_CREATURE));
    }

    private HorobiDeathsWail(final HorobiDeathsWail card) {
        super(card);
    }

    @Override
    public HorobiDeathsWail copy() {
        return new HorobiDeathsWail(this);
    }

}
