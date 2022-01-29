package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwinshotSniper extends CardImpl {

    public TwinshotSniper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Twinshot Sniper enters the battlefield, it deals 2 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Channel — {1}{R}, Discard Twinshot Sniper: It deals 2 damage to any target.
        ability = new ChannelAbility("{1}{R}", new DamageTargetEffect(2));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private TwinshotSniper(final TwinshotSniper card) {
        super(card);
    }

    @Override
    public TwinshotSniper copy() {
        return new TwinshotSniper(this);
    }
}
