package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsTargetOpponentControlsCount;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TombBlade extends CardImpl {

    public TombBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Tomb Blade deals combat damage to a player, that player loses life equal to the number of creatures they control unless they sacrifice a creature.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new LoseLifeTargetEffect(new PermanentsTargetOpponentControlsCount(StaticFilters.FILTER_PERMANENT_CREATURE))
                        .setText("that player loses life equal to the number of creatures they control"),
                        new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE)).withTheyText(),
                false, true
        ));

        // Unearth {6}{B}{B}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{6}{B}{B}")));
    }

    private TombBlade(final TombBlade card) {
        super(card);
    }

    @Override
    public TombBlade copy() {
        return new TombBlade(this);
    }
}
