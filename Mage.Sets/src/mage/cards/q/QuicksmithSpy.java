package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceOnBattlefieldControlUnchangedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.TargetPermanent;
import mage.watchers.common.LostControlWatcher;

/**
 *
 * @author Styxo
 */
public final class QuicksmithSpy extends CardImpl {

    public QuicksmithSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Quicksmith Spy enters the battlefield, target artifact you control gains "{T}: Draw a card" for as long as you control Quicksmith Spy.
        Ability artifactAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                new GainAbilityTargetEffect(artifactAbility, Duration.Custom),
                new SourceOnBattlefieldControlUnchangedCondition(),
                "target artifact you control gains \"{T}: Draw a card\" for as long as you control {this}");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetPermanent(new FilterControlledArtifactPermanent()));
        ability.addWatcher(new LostControlWatcher());
        this.addAbility(ability);
    }

    private QuicksmithSpy(final QuicksmithSpy card) {
        super(card);
    }

    @Override
    public QuicksmithSpy copy() {
        return new QuicksmithSpy(this);
    }
}
