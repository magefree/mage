package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StreambedAquitects extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.MERFOLK, "Merfolk creature");

    public StreambedAquitects(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {tap}: Target Merfolk creature gets +1/+1 and gains islandwalk until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(
                1, 1, Duration.EndOfTurn
        ).setText("target Merfolk creature gets +1/+1"), new TapSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(
                new IslandwalkAbility(false), Duration.EndOfTurn
        ).setText("and gains islandwalk until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // {tap}: Target land becomes an Island until end of turn.
        ability = new SimpleActivatedAbility(new BecomesBasicLandTargetEffect(Duration.EndOfTurn, SubType.ISLAND), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private StreambedAquitects(final StreambedAquitects card) {
        super(card);
    }

    @Override
    public StreambedAquitects copy() {
        return new StreambedAquitects(this);
    }
}
