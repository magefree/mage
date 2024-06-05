package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.HalfValue;
import mage.abilities.dynamicvalue.common.SacrificeCostManaValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ImskirIronEater extends CardImpl {

    private static final DynamicValue xValue = new HalfValue(ArtifactYouControlCount.instance, false);

    public ImskirIronEater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // When Imskir Iron-Eater enters the battlefield, you draw X cards and you lose X life, where X is half the number of artifacts you control, rounded down.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(xValue)
                        .setText("you draw X cards")
        );
        ability.addEffect(
                new LoseLifeSourceControllerEffect(xValue)
                        .setText("and you lose X life, where X is half the number of artifacts you control, rounded down.")
        );
        this.addAbility(ability.addHint(ArtifactYouControlHint.instance));

        // {3}{R}, Sacrifice an artifact: Imskir deals damage equal to the sacrificed artifact's mana value to any target.
        ability = new SimpleActivatedAbility(
                new DamageTargetEffect(SacrificeCostManaValue.ARTIFACT)
                        .setText("{this} deals damage equal to the sacrificed artifact's mana value to any target"),
                new ManaCostsImpl<>("{3}{R}")
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ImskirIronEater(final ImskirIronEater card) {
        super(card);
    }

    @Override
    public ImskirIronEater copy() {
        return new ImskirIronEater(this);
    }
}
