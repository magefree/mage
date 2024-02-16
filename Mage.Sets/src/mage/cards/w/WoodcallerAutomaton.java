package mage.cards.w;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.Optional;
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
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new UntapTargetEffect()),
                CastFromEverywhereSourceCondition.instance, "When {this} enters the battlefield, " +
                "if you cast it, untap target land you control. It becomes a Treefolk creature with haste " +
                "and base power and toughness equal to {this}'s power and toughness. It's still a land."
        );
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
        Optional<Permanent> optionalPermanent = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull);
        int power = optionalPermanent
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
        int toughness = optionalPermanent
                .map(MageObject::getToughness)
                .map(MageInt::getValue)
                .orElse(0);
        game.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(power, toughness, "", SubType.TREEFOLK),
                false, true, Duration.Custom
        ), source);
        return true;
    }
}
