package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SpellControlledDealsDamageTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlameChanneler extends CardImpl {

    public FlameChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.e.EmbodimentOfFlame.class;

        // When a spell you control deals damage, transform Flame Channeler.
        this.addAbility(new TransformAbility());
        this.addAbility(new SpellControlledDealsDamageTriggeredAbility(Zone.BATTLEFIELD,
                new TransformSourceEffect(), StaticFilters.FILTER_SPELL, false
        ).setTriggerPhrase("When a spell you control deals damage, "));
    }

    private FlameChanneler(final FlameChanneler card) {
        super(card);
    }

    @Override
    public FlameChanneler copy() {
        return new FlameChanneler(this);
    }
}
