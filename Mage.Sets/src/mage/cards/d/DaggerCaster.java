package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DaggerCaster extends CardImpl {

    public DaggerCaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Dagger Caster enters the battlefield, it deals 1 damage to each opponent and 1 damage to each creature your opponents control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamagePlayersEffect(
                1, TargetController.OPPONENT, "it"
        ));
        ability.addEffect(
                new DamageAllEffect(1, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE)
                        .setText("and 1 damage to each creature your opponents control")
        );
        this.addAbility(ability);
    }

    private DaggerCaster(final DaggerCaster card) {
        super(card);
    }

    @Override
    public DaggerCaster copy() {
        return new DaggerCaster(this);
    }
}
