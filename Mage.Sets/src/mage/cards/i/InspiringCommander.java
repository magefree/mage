package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class InspiringCommander extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another creature with power 2 or less");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public InspiringCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever another creature with power 2 or less enters the battlefield under your control, you gain 1 life and draw a card.
        Effect effect1 = new GainLifeEffect(1);
        Effect effect2 = new DrawCardSourceControllerEffect(1);
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, effect1, filter, false);
        ability.addEffect(effect2.setText("and draw a card"));
        this.addAbility(ability);
    }

    private InspiringCommander(final InspiringCommander card) {
        super(card);
    }

    @Override
    public InspiringCommander copy() {
        return new InspiringCommander(this);
    }
}
