package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author NinthWorld
 */
public final class UWing extends CardImpl {

    public UWing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // As long as U-Wing is tapped, it gets +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                SourceTappedCondition.TAPPED,
                "As long as {this} is tapped, it gets +1/+0")));

        // As long as U-Wing is untapped, it gets +0/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(0, 1, Duration.WhileOnBattlefield),
                SourceUntappedCondition.instance,
                "As long as {this} is untapped, it gets +0/+1")));
    }

    private UWing(final UWing card) {
        super(card);
    }

    @Override
    public UWing copy() {
        return new UWing(this);
    }
}

enum SourceUntappedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null) {
            return !permanent.isTapped();
        }
        return false;
    }
}
