
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class SkeletalScrying extends CardImpl {

    public SkeletalScrying(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{B}");


        // As an additional cost to cast Skeletal Scrying, exile X cards from your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SkeletalScryingRuleEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        // You draw X cards and you lose X life.
        this.getSpellAbility().addEffect(new SkeletalScryingEffect(new ManacostVariableValue()));

    }

    public SkeletalScrying(final SkeletalScrying card) {
        super(card);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        if (xValue > 0) {
            ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(xValue, xValue, new FilterCard("cards from your graveyard"))));
        }
    }

    @Override
    public SkeletalScrying copy() {
        return new SkeletalScrying(this);
    }
}

class SkeletalScryingRuleEffect extends OneShotEffect {

    public SkeletalScryingRuleEffect() {
        super(Outcome.Benefit);
        this.staticText = "as an additional cost to cast this spell, exile X cards from your graveyard";
    }

    public SkeletalScryingRuleEffect(final SkeletalScryingRuleEffect effect) {
        super(effect);
    }

    @Override
    public SkeletalScryingRuleEffect copy() {
        return new SkeletalScryingRuleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
class SkeletalScryingEffect extends OneShotEffect {

    protected DynamicValue amount;

    public SkeletalScryingEffect(int amount) {
        this(new StaticValue(amount));
    }

    public SkeletalScryingEffect(DynamicValue amount) {
        super(Outcome.Neutral);
        this.amount = amount.copy();
        staticText = "You draw " + amount + " cards and you lose " + amount + " life";
    }

    public SkeletalScryingEffect(final SkeletalScryingEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public SkeletalScryingEffect copy() {
        return new SkeletalScryingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
            if ( controller != null ) {
                controller.drawCards(amount.calculate(game, source, this), game);
                controller.loseLife(amount.calculate(game, source, this), game, false);
                return true;
            }
        return false;
    }
}
