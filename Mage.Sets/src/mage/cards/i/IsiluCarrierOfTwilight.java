package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.PersistAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IsiluCarrierOfTwilight extends CardImpl {

    public IsiluCarrierOfTwilight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.color.setBlack(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Each other nontoken creature you control has persist.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new PersistAbility(), Duration.WhileControlled,
                StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN, true
        ).setText("each other nontoken creature you control has persist")));

        // At the beginning of your first main phase, you may pay {W}. If you do, transform Isilu.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{W}"))
        ));
    }

    private IsiluCarrierOfTwilight(final IsiluCarrierOfTwilight card) {
        super(card);
    }

    @Override
    public IsiluCarrierOfTwilight copy() {
        return new IsiluCarrierOfTwilight(this);
    }
}
