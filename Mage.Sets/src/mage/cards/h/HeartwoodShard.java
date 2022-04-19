package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class HeartwoodShard extends CardImpl {

    public HeartwoodShard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {tap} or {G}, {tap}: Target creature gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn),
                new OrCost(
                        "{3}, {T} or {G}, {T}", new CompositeCost(new GenericManaCost(3), new TapSourceCost(), "{3}, {T}"),
                        new CompositeCost(new ManaCostsImpl<>("{G}"), new TapSourceCost(), "{G}, {T}")
                )
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HeartwoodShard(final HeartwoodShard card) {
        super(card);
    }

    @Override
    public HeartwoodShard copy() {
        return new HeartwoodShard(this);
    }
}
