
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 * @author LevelX2
 */
public final class NezumiShortfang extends CardImpl {

    public NezumiShortfang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.flipCard = true;
        this.flipCardName = "Stabwhisker the Odious";

        CreatureToken flipToken = new CreatureToken(3, 3, "", SubType.RAT, SubType.SHAMAN)
            .withName("Stabwhisker the Odious")
            .withSuperType(SuperType.LEGENDARY)
            .withColor("B")
            .withAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.OPPONENT,
                new StabwhiskerLoseLifeEffect(),
                false
            ));

        // {1}{B}, {tap}: Target opponent discards a card. Then if that player has no cards in hand, flip Nezumi Shortfang.
        Ability ability = new SimpleActivatedAbility(new DiscardTargetEffect(1), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        ability.addEffect(new ConditionalOneShotEffect(
            new FlipSourceEffect(flipToken),
            new CardsInTargetOpponentHandCondition(ComparisonType.FEWER_THAN, 1),
            "Then if that player has no cards in hand, flip {this}"));
        this.addAbility(ability);
    }

    private NezumiShortfang(final NezumiShortfang card) {
        super(card);
    }

    @Override
    public NezumiShortfang copy() {
        return new NezumiShortfang(this);
    }
}

class StabwhiskerLoseLifeEffect extends OneShotEffect {

    StabwhiskerLoseLifeEffect() {
        super(Outcome.LoseLife);
        this.staticText = "that player loses 1 life for each card fewer than three in their hand";
    }

    private StabwhiskerLoseLifeEffect(final StabwhiskerLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public StabwhiskerLoseLifeEffect copy() {
        return new StabwhiskerLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            int lifeLose = 3 - opponent.getHand().size();
            if (lifeLose > 0) {
                opponent.loseLife(lifeLose, game, source, false);
            }
            return true;
        }
        return false;
    }
}

class CardsInTargetOpponentHandCondition implements Condition {

    private Condition condition;
    private ComparisonType type;
    private int count;

    public CardsInTargetOpponentHandCondition() {
        this(ComparisonType.EQUAL_TO, 0);
    }

    public CardsInTargetOpponentHandCondition(ComparisonType type, int count) {
        this.type = type;
        this.count = count;
    }

    public CardsInTargetOpponentHandCondition(ComparisonType type, int count, Condition conditionToDecorate) {
        this(type, count);
        this.condition = conditionToDecorate;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        conditionApplies = ComparisonType.compare(opponent.getHand().size(), type, count);

        //If a decorated condition exists, check it as well and apply them together.
        if (this.condition != null) {
            conditionApplies = conditionApplies && this.condition.apply(game, source);
        }

        return conditionApplies;
    }
}
