package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvatarKyoshiEarthbender extends CardImpl {

    public AvatarKyoshiEarthbender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // During your turn, Avatar Kyoshi has hexproof.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "during your turn, {this} has hexproof"
        )));

        // At the beginning of combat on your turn, earthbend 8, then untap that land.
        Ability ability = new BeginningOfCombatTriggeredAbility(new EarthbendTargetEffect(8, false));
        ability.addEffect(new UntapTargetEffect().setText(", then untap that land"));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);
    }

    private AvatarKyoshiEarthbender(final AvatarKyoshiEarthbender card) {
        super(card);
    }

    @Override
    public AvatarKyoshiEarthbender copy() {
        return new AvatarKyoshiEarthbender(this);
    }
}
