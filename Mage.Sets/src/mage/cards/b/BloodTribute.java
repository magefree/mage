package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author North
 */
public final class BloodTribute extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.VAMPIRE, "an untapped Vampire you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public BloodTribute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Kicker - Tap an untapped Vampire you control.
        this.addAbility(new KickerAbility(new TapTargetCost(new TargetControlledPermanent(filter))));

        // Target opponent loses half their life, rounded up.
        this.getSpellAbility().addEffect(new BloodTributeLoseLifeEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // If Blood Tribute was kicked, you gain life equal to the life lost this way.
        Effect effect = new ConditionalOneShotEffect(
                new BloodTributeGainLifeEffect(),
                KickedCondition.ONCE,
                "if this spell was kicked, you gain life equal to the life lost this way");
        this.getSpellAbility().addEffect(effect);
    }

    private BloodTribute(final BloodTribute card) {
        super(card);
    }

    @Override
    public BloodTribute copy() {
        return new BloodTribute(this);
    }
}

class BloodTributeLoseLifeEffect extends OneShotEffect {

    public BloodTributeLoseLifeEffect() {
        super(Outcome.Damage);
        this.staticText = "Target opponent loses half their life, rounded up";
    }

    public BloodTributeLoseLifeEffect(final BloodTributeLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public BloodTributeLoseLifeEffect copy() {
        return new BloodTributeLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            Integer amount = (int) Math.ceil(player.getLife() / 2f);
            if (amount > 0) {
                player.loseLife(amount, game, source, false);
                game.getState().setValue(source.getSourceId().toString() + "_BloodTribute", amount);
                return true;
            }
        }
        return false;
    }
}

class BloodTributeGainLifeEffect extends OneShotEffect {

    public BloodTributeGainLifeEffect() {
        super(Outcome.GainLife);
        this.staticText = "If Blood Tribute was kicked, you gain life equal to the life lost this way";
    }

    public BloodTributeGainLifeEffect(final BloodTributeGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public BloodTributeGainLifeEffect copy() {
        return new BloodTributeGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Integer amount = (Integer) game.getState().getValue(source.getSourceId().toString() + "_BloodTribute");
            if (amount != null && amount > 0) {
                player.gainLife(amount, game, source);
                return true;
            }
        }
        return false;
    }
}
