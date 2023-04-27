package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SlobadGoblinTinkerer extends CardImpl {

    public SlobadGoblinTinkerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Sacrifice an artifact: Target artifact is indestructible this turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT)
        );
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private SlobadGoblinTinkerer(final SlobadGoblinTinkerer card) {
        super(card);
    }

    @Override
    public SlobadGoblinTinkerer copy() {
        return new SlobadGoblinTinkerer(this);
    }
}
