package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.*;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SephirothFabledSOLDIER extends CardImpl {

    public SephirothFabledSOLDIER(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.s.SephirothOneWingedAngel.class;

        // Whenever Sephiroth enters or attacks, you may sacrifice another creature. If you do, draw a card.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE)
        )));

        // Whenever another creature dies, target opponent loses 1 life and you gain 1 life. If this is the fourth time this ability has resolved this turn, transform Sephiroth.
        this.addAbility(new TransformAbility());
        Ability ability = new DiesCreatureTriggeredAbility(
                new LoseLifeTargetEffect(1), false,
                StaticFilters.FILTER_ANOTHER_CREATURE
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(4, new TransformSourceEffect()));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private SephirothFabledSOLDIER(final SephirothFabledSOLDIER card) {
        super(card);
    }

    @Override
    public SephirothFabledSOLDIER copy() {
        return new SephirothFabledSOLDIER(this);
    }
}
