package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GalliaOfTheEndlessDance extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SATYR, "");

    public GalliaOfTheEndlessDance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SATYR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Other Satyrs you control get +1/+1 and have haste.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        ).setText("other Satyrs you control get +1/+1"));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ).setText("and have haste"));

        // Whenever you attack with three or more creatures, you may discard a card at random. If you do, draw two cards.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(2), new DiscardCardCost(true)
        ), 3));
    }

    private GalliaOfTheEndlessDance(final GalliaOfTheEndlessDance card) {
        super(card);
    }

    @Override
    public GalliaOfTheEndlessDance copy() {
        return new GalliaOfTheEndlessDance(this);
    }
}
