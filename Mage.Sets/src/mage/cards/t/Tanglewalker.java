
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DefendingPlayerControlsCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author North
 */
public final class Tanglewalker extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public Tanglewalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.DRYAD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each creature you control can't be blocked as long as defending player controls an artifact land.
        Effect effect = new ConditionalRestrictionEffect(
                new CantBeBlockedAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURES, Duration.WhileOnBattlefield),
                new DefendingPlayerControlsCondition(filter));
        effect.setText("Each creature you control can't be blocked as long as defending player controls an artifact land");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private Tanglewalker(final Tanglewalker card) {
        super(card);
    }

    @Override
    public Tanglewalker copy() {
        return new Tanglewalker(this);
    }
}
