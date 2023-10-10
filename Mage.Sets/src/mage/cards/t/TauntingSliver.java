package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TauntingSliver extends CardImpl {

    public TauntingSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sliver creatures you control have "When this creature enters the battlefield, goad target creature an opponent controls."
        Ability ability = new EntersBattlefieldTriggeredAbility(new GoadTargetEffect())
                .setTriggerPhrase("When this creature enters the battlefield, ");
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                ability, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_SLIVERS
        )));
    }

    private TauntingSliver(final TauntingSliver card) {
        super(card);
    }

    @Override
    public TauntingSliver copy() {
        return new TauntingSliver(this);
    }
}
