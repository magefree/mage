package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.common.TheRingTemptsYouTriggeredAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SauronTheDarkLord extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("legendary artifact or legendary creature");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.ARMY, "an Army you control");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public SauronTheDarkLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Ward--Sacrifice a legendary artifact or legendary creature.
        this.addAbility(new WardAbility(new SacrificeTargetCost(filter), false));

        // Whenever an opponent casts a spell, amass Orcs 1.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new AmassEffect(1, SubType.ORC), false
        ));

        // Whenever an Army you control deals combat damage to a player, the Ring tempts you.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new TheRingTemptsYouEffect(), filter2, false,
                SetTargetPointer.NONE, true
        ));

        // Whenever the Ring tempts you, you may discard your hand. If you do, draw four cards.
        this.addAbility(new TheRingTemptsYouTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(4), new DiscardHandCost())
        ));
    }

    private SauronTheDarkLord(final SauronTheDarkLord card) {
        super(card);
    }

    @Override
    public SauronTheDarkLord copy() {
        return new SauronTheDarkLord(this);
    }
}
