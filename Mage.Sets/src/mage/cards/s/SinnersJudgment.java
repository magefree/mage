package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

import java.util.Optional;
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
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of your upkeep, put a judgment counter on Sinner's Judgment. Then if there are three or more judgment counters on it, enchanted player loses the game.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.JUDGMENT.createInstance()));
        ability.addEffect(new SinnersJudgmentEffect());
        this.addAbility(ability);

        // If Sinner's Judgment would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(DisturbAbility.makeBackAbility());
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
        staticText = "Then if there are three or more judgment counters on it, enchanted player loses the game";
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
        return Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .filter(permanent -> permanent.getCounters(game).getCount(CounterType.JUDGMENT) >= 3)
                .map(Permanent::getAttachedTo)
                .map(game::getPlayer)
                .filter(player -> {
                    player.lost(game);
                    return true;
                })
                .isPresent();
    }
}
