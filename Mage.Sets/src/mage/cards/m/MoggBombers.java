package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author jeffwadsworth
 */
public final class MoggBombers extends CardImpl {
    
    private static final String rule = "When another creature enters the battlefield, sacrifice {this} and it deals 3 damage to target player or planeswalker.";

    public MoggBombers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When another creature enters the battlefield, sacrifice Mogg Bombers and it deals 3 damage to target player.
        Effect sacrificeMoggBombers = new SacrificeSourceEffect();
        Effect damageTargetPlayer = new DamageTargetEffect(3);
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                sacrificeMoggBombers,
                StaticFilters.FILTER_ANOTHER_CREATURE,
                false,
                rule);
        ability.addEffect(damageTargetPlayer);
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
