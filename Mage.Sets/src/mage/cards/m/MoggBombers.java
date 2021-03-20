package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class MoggBombers extends CardImpl {
    
    private static final String rule = "When another creature enters the battlefield, sacrifice {this} and it deals 3 damage to target player.";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

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
                filter, false, rule);
        ability.addEffect(damageTargetPlayer);
        ability.addTarget(new TargetPlayer());
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
