package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.IncrementAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FractalToken;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AmbitiousAugmenter extends CardImpl {

    public AmbitiousAugmenter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Increment
        this.addAbility(new IncrementAbility());

        // When this creature dies, if it had one or more counters on it, create a 0/0 green and blue Fractal creature token, then put this creature's counters on that token.
        this.addAbility(new DiesSourceTriggeredAbility(
                new AmbitiousAugmenterEffect()
        ).withInterveningIf(AmbitiousAugmenterCondition.instance));
    }

    private AmbitiousAugmenter(final AmbitiousAugmenter card) {
        super(card);
    }

    @Override
    public AmbitiousAugmenter copy() {
        return new AmbitiousAugmenter(this);
    }
}

enum AmbitiousAugmenterCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .getEffectValueFromAbility(source, "permanentLeftBattlefield", Permanent.class)
                .filter(permanent -> permanent.getCounters(game).getTotalCount() >= 1)
                .isPresent();
    }

    @Override
    public String toString() {
        return "if it had one or more counters on it";
    }
}

class AmbitiousAugmenterEffect extends OneShotEffect {

    AmbitiousAugmenterEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a 0/0 green and blue Fractal creature token, "
                + "then put this creature's counters on that token";
    }

    private AmbitiousAugmenterEffect(final AmbitiousAugmenterEffect effect) {
        super(effect);
    }

    @Override
    public AmbitiousAugmenterEffect copy() {
        return new AmbitiousAugmenterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return false;
        }

        CreateTokenEffect effect = new CreateTokenEffect(new FractalToken());
        if (!effect.apply(game, source)) {
            return false;
        }

        for (UUID id : effect.getLastAddedTokenIds()) {
            Permanent token = game.getPermanent(id);
            if (token == null) {
                continue;
            }
            for (Counter counter : permanent.getCounters(game).copy().values()) {
                token.addCounters(counter, source, game);
            }
        }
        return true;
    }
}