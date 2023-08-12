package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ErebosBleakHearted extends CardImpl {

    public ErebosBleakHearted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to black is less than five, Erebos isn't a creature.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(DevotionCount.B, 5))
                .addHint(DevotionCount.B.getHint()));

        // Whenever another creature you control dies, you may pay 2 life. If you do, draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new PayLifeCost(2)
        ), false, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL, true));

        // {1}{B}, Sacrifice another creature: Target creature gets -2/-1 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(-2, -1), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
        ));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ErebosBleakHearted(final ErebosBleakHearted card) {
        super(card);
    }

    @Override
    public ErebosBleakHearted copy() {
        return new ErebosBleakHearted(this);
    }
}
