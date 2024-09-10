package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author jeffwadsworth
 */
public final class MoggBombers extends CardImpl {

    public MoggBombers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When another creature enters the battlefield, sacrifice Mogg Bombers and it deals 3 damage to target player.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD,
                new SacrificeSourceEffect(), StaticFilters.FILTER_ANOTHER_CREATURE, false);
        ability.addEffect(new DamageTargetEffect(3, "it").concatBy("and"));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);

    }

    private MoggBombers(final MoggBombers card) {
        super(card);
    }

    @Override
    public MoggBombers copy() {
        return new MoggBombers(this);
    }
}
