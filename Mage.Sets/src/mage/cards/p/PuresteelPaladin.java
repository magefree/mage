

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 * @author Loki
 */
public final class PuresteelPaladin extends CardImpl {
    private static final FilterPermanent filter = new FilterControlledPermanent("Equipment");

    static {
        filter.add(new SubtypePredicate(SubType.EQUIPMENT));
    }

    public PuresteelPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an Equipment enters the battlefield under your control, you may draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), filter, true));
        // <i>Metalcraft</i> &mdash; Equipment you control have equip {0} as long as you control three or more artifacts
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(new EquipAbility(Outcome.AddAbility, new GenericManaCost(0)), Duration.WhileOnBattlefield, filter),
                MetalcraftCondition.instance,
                "<i>Metalcraft</i> &mdash; Equipment you control have equip {0} as long as you control three or more artifacts")));
    }

    public PuresteelPaladin(final PuresteelPaladin card) {
        super(card);
    }

    @Override
    public PuresteelPaladin copy() {
        return new PuresteelPaladin(this);
    }
}
