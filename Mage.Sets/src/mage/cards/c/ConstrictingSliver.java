package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class ConstrictingSliver extends CardImpl {

    public ConstrictingSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sliver creatures you control have "When this creature enters the battlefield, you may exile target creature an opponent controls
        // until this creature leaves the battlefield."
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect(), true);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(ability,
                        Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ALL_SLIVERS)
                        .setText("Sliver creatures you control have \"When this creature enters the battlefield, "
                                + "you may exile target creature an opponent controls until this creature leaves the battlefield.\"")));
    }

    private ConstrictingSliver(final ConstrictingSliver card) {
        super(card);
    }

    @Override
    public ConstrictingSliver copy() {
        return new ConstrictingSliver(this);
    }
}
