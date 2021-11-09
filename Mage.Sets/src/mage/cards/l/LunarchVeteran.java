package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LunarchVeteran extends CardImpl {

    public LunarchVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.l.LuminousPhantom.class;

        // Whenever another creature enters the battlefield under your control, you gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new GainLifeEffect(1), StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ));

        // Disturb {1}{W}
        this.addAbility(new DisturbAbility(this, "{1}{W}"));
    }

    private LunarchVeteran(final LunarchVeteran card) {
        super(card);
    }

    @Override
    public LunarchVeteran copy() {
        return new LunarchVeteran(this);
    }
}
