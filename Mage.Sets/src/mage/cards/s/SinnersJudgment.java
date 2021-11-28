package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SinnersJudgment extends CardImpl {

    public SinnersJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.CURSE);
        this.color.setWhite(true);
        this.nightCard = true;

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of your upkeep, put a judgment counter on Sinner's Judgment. Then if there are three or more judgment counters on it, enchanted player loses the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SinnersJudgmentEffect(), TargetController.YOU, false
        ));

        // If Sinner's Judgment would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new ExileSourceEffect().setText("exile it instead")));
    }

    private SinnersJudgment(final SinnersJudgment card) {
        super(card);
    }

    @Override
    public SinnersJudgment copy() {
        return new SinnersJudgment(this);
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
