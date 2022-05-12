
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WarriorToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class MarduCharm extends CardImpl {

    private static final FilterCard filter = new FilterCard("a noncreature, nonland card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public MarduCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}{W}{B}");


        // Choose one -
        // <strong>*</strong> Mardu Charm deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // <strong>*</strong> Create two 1/1 white Warrior creature tokens. They gain first strike until end of turn.
        Mode mode = new Mode(new MarduCharmCreateTokenEffect());
        this.getSpellAbility().addMode(mode);

        // <strong>*</strong> Target opponent reveals their hand. You choose a noncreature, nonland card from it.  That player discards that card.
        mode = new Mode(new DiscardCardYouChooseTargetEffect(filter));
        mode.addTarget(new TargetOpponent());
        this.getSpellAbility().addMode(mode);

    }

    private MarduCharm(final MarduCharm card) {
        super(card);
    }

    @Override
    public MarduCharm copy() {
        return new MarduCharm(this);
    }
}

class MarduCharmCreateTokenEffect extends OneShotEffect {

    public MarduCharmCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create two 1/1 white Warrior creature tokens. They gain first strike until end of turn";
    }

    public MarduCharmCreateTokenEffect(final MarduCharmCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public MarduCharmCreateTokenEffect copy() {
        return new MarduCharmCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new WarriorToken(), 2);
            effect.apply(game, source);
            for (UUID tokenId :effect.getLastAddedTokenIds()) {
                Permanent token = game.getPermanent(tokenId);
                if (token != null) {
                    ContinuousEffect continuousEffect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
                    continuousEffect.setTargetPointer(new FixedTarget(tokenId));
                    game.addEffect(continuousEffect, source);
                }
            }
            return true;
        }
        return false;
    }
}
