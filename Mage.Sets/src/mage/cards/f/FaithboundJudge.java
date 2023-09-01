package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.*;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaithboundJudge extends TransformingDoubleFacedCard {

    private static final Condition condition1 = new SourceHasCounterCondition(CounterType.JUDGMENT, 0, 2);
    private static final Condition condition2 = new SourceHasCounterCondition(CounterType.JUDGMENT, 3);

    public FaithboundJudge(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT, SubType.SOLDIER}, "{1}{W}{W}",
                "Sinner's Judgment",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA, SubType.CURSE}, "W"
        );
        this.getLeftHalfCard().setPT(4, 4);

        // Defender
        this.getLeftHalfCard().addAbility(DefenderAbility.getInstance());

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // At the beginning of your upkeep, if Faithbound Judge has two or fewer judgment counters on it, put a judgment counter on it.
        this.getLeftHalfCard().addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.JUDGMENT.createInstance()),
                        TargetController.YOU, false
                ), condition1, "At the beginning of your upkeep, if {this} has " +
                "two or fewer judgment counters on it, put a judgment counter on it."
        ));

        // As long as Faithbound Judge has three or more judgment counters on it, it can attack as though it didn't have defender.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield), condition2
        ).setText("as long as {this} has three or more judgment counters on it," +
                " it can attack as though it didn't have defender")));

        // Disturb {5}{W}{W}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{5}{W}{W}"));

        // Sinner's Judgment
        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // At the beginning of your upkeep, put a judgment counter on Sinner's Judgment. Then if there are three or more judgment counters on it, enchanted player loses the game.
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SinnersJudgmentEffect(), TargetController.YOU, false
        ));

        // If Sinner's Judgment would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeExileAbility());
    }

    private FaithboundJudge(final FaithboundJudge card) {
        super(card);
    }

    @Override
    public FaithboundJudge copy() {
        return new FaithboundJudge(this);
    }
}

class SinnersJudgmentEffect extends OneShotEffect {

    SinnersJudgmentEffect() {
        super(Outcome.Benefit);
        staticText = "put a judgment counter on {this}. Then if there are three " +
                "or more judgment counters on it, enchanted player loses the game";
    }

    private SinnersJudgmentEffect(final SinnersJudgmentEffect effect) {
        super(effect);
    }

    @Override
    public SinnersJudgmentEffect copy() {
        return new SinnersJudgmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.JUDGMENT.createInstance(), source, game);
        if (permanent.getCounters(game).getCount(CounterType.JUDGMENT) < 3) {
            return true;
        }
        Player player = game.getPlayer(permanent.getAttachedTo());
        if (player != null) {
            player.lost(game);
        }
        return true;
    }
}
