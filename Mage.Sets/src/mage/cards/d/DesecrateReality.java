package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValueParityPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DesecrateReality extends CardImpl {

    public DesecrateReality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{7}");


        // For each opponent, exile up to one target permanent that player controls with an even mana value.
        this.getSpellAbility().addEffect(new ExileTargetEffect()
            .setTargetPointer(new EachTargetPointer())
            .setText("for each opponent, exile up to one target permanent that player controls with an even mana value."));
        this.getSpellAbility().setTargetAdjuster(DesecrateRealityAdjuster.instance);

        // Adamant -- If at least three colorless mana was spent to cast this spell, return a permanent card with an odd mana value from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new DesecrateRealityEffect(),
            AdamantCondition.COLORLESS, "<br><i>Adamant</i> &mdash; "
            + "If at least three colorless mana was spent to cast this spell, "
            + "return a permanent card with an odd mana value from your graveyard to the battlefield."
        ));
    }

    private DesecrateReality(final DesecrateReality card) {
        super(card);
    }

    @Override
    public DesecrateReality copy() {
        return new DesecrateReality(this);
    }
}

enum DesecrateRealityAdjuster implements TargetAdjuster {
    instance;

    private static final FilterControlledPermanent filterCount = new FilterControlledPermanent("");

    static {
        filterCount.add(ManaValueParityPredicate.EVEN);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);

            if (opponent == null || game.getBattlefield().count(
                filterCount,
                opponentId, ability, game
            ) < 1) {
                continue;
            }
            FilterPermanent filter = new FilterPermanent("permanent controlled by " + opponent.getName() + " with an even mana value.");
            filter.add(new ControllerIdPredicate(opponentId));
            filter.add(ManaValueParityPredicate.EVEN);
            TargetPermanent targetPermanent = new TargetPermanent(0, 1, filter);
            ability.addTarget(targetPermanent);
        }
    }
}

class DesecrateRealityEffect extends OneShotEffect {

    private static final FilterCard filter
        = new FilterPermanentCard("permanent card with an odd mana value in your graveyard");

    static {
        filter.add(ManaValueParityPredicate.ODD);
    }

    DesecrateRealityEffect() {
        super(Outcome.PutCardInPlay);
    }

    private DesecrateRealityEffect(final DesecrateRealityEffect effect) {
        super(effect);
    }

    @Override
    public DesecrateRealityEffect copy() {
        return new DesecrateRealityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // return a permanent card with an odd mana value from your graveyard to the battlefield.
        Target target = new TargetCardInYourGraveyard(filter);
        target.setNotTarget(true);
        if (controller.choose(outcome, target, source, game)) {
            Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
            effect.setTargetPointer(new FixedTarget(target.getFirstTarget(), game));
            effect.apply(game, source);
        }
        return true;
    }
}
