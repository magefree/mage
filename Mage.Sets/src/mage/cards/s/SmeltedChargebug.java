package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmeltedChargebug extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SmeltedChargebug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Smelted Chargebug enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // Whenever Smelted Chargebug attacks, you may pay {E}. If you do, another target attacking creature gets +1/+0 and gains menace until end of turn.
        Ability ability = new AttacksTriggeredAbility(new DoIfCostPaid(
                new BoostTargetEffect(1, 0)
                        .setText("another target attacking creature gets +1/+0"),
                new PayEnergyCost(1)
        ).addEffect(new GainAbilityTargetEffect(new MenaceAbility(false))
                .setText("and gains menace until end of turn")));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SmeltedChargebug(final SmeltedChargebug card) {
        super(card);
    }

    @Override
    public SmeltedChargebug copy() {
        return new SmeltedChargebug(this);
    }
}
