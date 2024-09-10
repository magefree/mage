package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.turn.SkipNextTurnSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class ChronatogTotem extends CardImpl {

    public ChronatogTotem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {tap}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {1}{U}: Chronatog Totem becomes a 1/2 blue Atog artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(1, 2, "1/2 blue Atog artifact creature")
                        .withColor("U")
                        .withSubType(SubType.ATOG)
                        .withType(CardType.ARTIFACT),
                CardType.ARTIFACT, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{U}")));

        // {0}: Chronatog Totem gets +3/+3 until end of turn. You skip your next turn. Activate this ability only once each turn and only if Chronatog Totem is a creature.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(3, 3, Duration.EndOfTurn),
                new GenericManaCost(0), 1,
                ChronatogTotemCondition.instance
        );
        ability.addEffect(new SkipNextTurnSourceEffect());
        this.addAbility(ability);
    }

    private ChronatogTotem(final ChronatogTotem card) {
        super(card);
    }

    @Override
    public ChronatogTotem copy() {
        return new ChronatogTotem(this);
    }
}

enum ChronatogTotemCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return permanent.isCreature(game);
        }
        return false;
    }

    @Override
    public String toString() {
        return "{this} is a creature";
    }
}
