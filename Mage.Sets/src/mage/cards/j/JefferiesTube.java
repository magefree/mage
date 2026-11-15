package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author muz
 */
public final class JefferiesTube extends CardImpl {

    public JefferiesTube(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {2}, {T}: Put two charge counters on target artifact.
        Ability ability = new SimpleActivatedAbility(
            new AddCountersTargetEffect(CounterType.CHARGE.createInstance(2)),
            new ManaCostsImpl<>("{2}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);

        // {3}, {T}: Target creature you control can't be blocked this turn.
        Ability ability2 = new SimpleActivatedAbility(
            new CantBeBlockedTargetEffect(),
            new ManaCostsImpl<>("{3}")
        );
        ability2.addCost(new TapSourceCost());
        ability2.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability2);

        // {4}, {T}: Draw a card. You may put a land card from your hand onto the battlefield tapped.
        Ability ability3 = new SimpleActivatedAbility(
            new DrawCardSourceControllerEffect(1),
            new ManaCostsImpl<>("{4}")
        );
        ability3.addCost(new TapSourceCost());
        ability3.addEffect(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A, false, true));
        this.addAbility(ability3);
    }

    private JefferiesTube(final JefferiesTube card) {
        super(card);
    }

    @Override
    public JefferiesTube copy() {
        return new JefferiesTube(this);
    }
}
