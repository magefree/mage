package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NyleasHuntmaster extends CardImpl {

    public NyleasHuntmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.CENTAUR, SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Nylea's Huntmaster enters the battlefield,
        // target creature you control gets +X/+0 until end of turn,
        // where X is your devotion to green.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(
                DevotionCount.G, StaticValue.get(0), Duration.EndOfTurn
        ));
        ability.addEffect(new InfoEffect(DevotionCount.G.getReminder()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addHint(DevotionCount.G.getHint());

        this.addAbility(ability);
    }

    private NyleasHuntmaster(final NyleasHuntmaster card) {
        super(card);
    }

    @Override
    public NyleasHuntmaster copy() {
        return new NyleasHuntmaster(this);
    }
}
