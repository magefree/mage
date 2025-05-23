package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.hint.common.NotMyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Elemental31TrampleHasteToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForceOfRage extends CardImpl {

    private static final FilterOwnedCard filter = new FilterOwnedCard("a red card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public ForceOfRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");

        // If it's not your turn, you may exile a red card from your hand rather than pay this spell's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(
                new ExileFromHandCost(new TargetCardInHand(filter)), NotMyTurnCondition.instance,
                "If it's not your turn, you may exile a red card from " +
                        "your hand rather than pay this spell's mana cost."
        ).addHint(NotMyTurnHint.instance));

        // Create two 3/1 red Elemental creature tokens with trample and haste. Sacrifice those tokens at the beginning of your next upkeep.
        this.getSpellAbility().addEffect(new ForceOfRageEffect());
    }

    private ForceOfRage(final ForceOfRage card) {
        super(card);
    }

    @Override
    public ForceOfRage copy() {
        return new ForceOfRage(this);
    }
}

class ForceOfRageEffect extends OneShotEffect {

    ForceOfRageEffect() {
        super(Outcome.Benefit);
        staticText = "Create two 3/1 red Elemental creature tokens with trample and haste. " +
                "Sacrifice those tokens at the beginning of your next upkeep.";
    }

    private ForceOfRageEffect(final ForceOfRageEffect effect) {
        super(effect);
    }

    @Override
    public ForceOfRageEffect copy() {
        return new ForceOfRageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new Elemental31TrampleHasteToken();
        token.putOntoBattlefield(2, game, source, source.getControllerId());
        List<Permanent> permanentList = new ArrayList();
        for (UUID permId : token.getLastAddedTokenIds()) {
            permanentList.add(game.getPermanent(permId));
        }
        Effect effect = new SacrificeTargetEffect("sacrifice those tokens");
        effect.setTargetPointer(new FixedTargets(permanentList, game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(effect), source);
        return true;
    }
}

