package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WoodcallerAutomaton extends CardImpl {

    public WoodcallerAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{10}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Prototype {2}{G}{G} -- 3/3
        this.addAbility(new PrototypeAbility(this, "{2}{G}{G}", 3, 3));

        // When Woodcaller Automaton enters the battlefield, if you cast it, untap target land you control. It becomes a Treefolk creature with haste and base power and toughness equal to Woodcaller Automaton's power and toughness. It's still a land.
        Ability ability = new EntersBattlefieldTriggeredAbility(new UntapTargetEffect())
                .withInterveningIf(CastFromEverywhereSourceCondition.instance);
        ability.addEffect(new WoodcallerAutomatonEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability);
    }

    private WoodcallerAutomaton(final WoodcallerAutomaton card) {
        super(card);
    }

    @Override
    public WoodcallerAutomaton copy() {
        return new WoodcallerAutomaton(this);
    }
}

class WoodcallerAutomatonEffect extends OneShotEffect {

    WoodcallerAutomatonEffect() {
        super(Outcome.Benefit);
        staticText = "It becomes a Treefolk creature with haste and base power and toughness equal to {this}'s power and toughness. It's still a land";
    }

    private WoodcallerAutomatonEffect(final WoodcallerAutomatonEffect effect) {
        super(effect);
    }

    @Override
    public WoodcallerAutomatonEffect copy() {
        return new WoodcallerAutomatonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int power = SourcePermanentPowerValue.ALLOW_NEGATIVE.calculate(game, source, this);
        int toughness = SourcePermanentToughnessValue.instance.calculate(game, source, this);
        game.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(power, toughness, "", SubType.TREEFOLK).withAbility(HasteAbility.getInstance()),
                false, true, Duration.Custom
        ), source);
        return true;
    }
}
