package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class QuicksmithRebel extends CardImpl {

    public QuicksmithRebel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Quicksmith Rebel enters the battlefield, target artifact you control gains "{T}: This artifact deals 2 damage to any target" for as long as you control Quicksmith Rebel.
        Ability artifactAbility = new SimpleActivatedAbility(new DamageTargetEffect(2), new TapSourceCost());
        artifactAbility.addTarget(new TargetAnyTarget());
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(artifactAbility, Duration.WhileControlled).setText("target artifact you control gains \"{T}: This artifact deals 2 damage to any target\" for as long as you control {this}"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        this.addAbility(ability);
    }

    private QuicksmithRebel(final QuicksmithRebel card) {
        super(card);
    }

    @Override
    public QuicksmithRebel copy() {
        return new QuicksmithRebel(this);
    }
}
