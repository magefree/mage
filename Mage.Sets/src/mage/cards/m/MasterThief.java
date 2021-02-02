package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldControlUnchangedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetArtifactPermanent;
import mage.watchers.common.LostControlWatcher;

/**
 * @author Loki, JayDi85
 */
public final class MasterThief extends CardImpl {

    public MasterThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Master Thief enters the battlefield, gain control of target artifact for as long as you control Master Thief.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.Custom),
                new SourceOnBattlefieldControlUnchangedCondition(),
                "gain control of target artifact for as long as you control {this}"),
                false);

        ability.addTarget(new TargetArtifactPermanent());
        ability.addWatcher(new LostControlWatcher());
        this.addAbility(ability);
    }

    private MasterThief(final MasterThief card) {
        super(card);
    }

    @Override
    public MasterThief copy() {
        return new MasterThief(this);
    }

}
