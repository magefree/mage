package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.DontCauseTriggerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Hushbringer extends CardImpl {

    public Hushbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Creatures entering the battlefield or dying don't cause abilities to trigger.
        this.addAbility(new SimpleStaticAbility(new DontCauseTriggerEffect(StaticFilters.FILTER_PERMANENT_CREATURES, true, null)));
    }

    private Hushbringer(final Hushbringer card) {
        super(card);
    }

    @Override
    public Hushbringer copy() {
        return new Hushbringer(this);
    }
}
