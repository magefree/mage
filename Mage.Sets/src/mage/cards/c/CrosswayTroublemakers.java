package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrosswayTroublemakers extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent(SubType.VAMPIRE, "attacking Vampires");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.VAMPIRE, "a Vampire you control");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public CrosswayTroublemakers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Attacking Vampires you control have deathtouch and lifelink.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("and lifelink"));
        this.addAbility(ability);

        // Whenever a Vampire you control dies, you may pay 2 life. If you do, draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new PayLifeCost(2)
        ), false, filter2));
    }

    private CrosswayTroublemakers(final CrosswayTroublemakers card) {
        super(card);
    }

    @Override
    public CrosswayTroublemakers copy() {
        return new CrosswayTroublemakers(this);
    }
}
