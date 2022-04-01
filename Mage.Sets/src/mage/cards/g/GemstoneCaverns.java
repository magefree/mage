package mage.cards.g;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.OpeningHandAction;
import mage.abilities.StaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ConditionalManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public final class GemstoneCaverns extends CardImpl {

    public GemstoneCaverns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        addSuperType(SuperType.LEGENDARY);

        // If Gemstone Caverns is in your opening hand and you're not playing first, you may begin the game with Gemstone Caverns on the battlefield with a luck counter on it. If you do, exile a card from your hand.
        this.addAbility(new GemstoneCavernsAbility());

        // {T}: Add {C}. If Gemstone Caverns has a luck counter on it, instead add one mana of any color.
        Ability ability = new ConditionalManaAbility(Zone.BATTLEFIELD,
                new ConditionalManaEffect(
                        new AddManaOfAnyColorEffect(),
                        new BasicManaEffect(Mana.ColorlessMana(1)),
                        new SourceHasCounterCondition(CounterType.LUCK),
                        "Add {C}. If {this} has a luck counter on it, instead add one mana of any color."),
                new TapSourceCost());
        this.addAbility(ability);
    }

    private GemstoneCaverns(final GemstoneCaverns card) {
        super(card);
    }

    @Override
    public GemstoneCaverns copy() {
        return new GemstoneCaverns(this);
    }
}

class GemstoneCavernsAbility extends StaticAbility implements OpeningHandAction {

    public GemstoneCavernsAbility() {
        super(Zone.HAND, new GemstoneCavernsEffect());
    }

    public GemstoneCavernsAbility(final GemstoneCavernsAbility ability) {
        super(ability);
    }

    @Override
    public GemstoneCavernsAbility copy() {
        return new GemstoneCavernsAbility(this);
    }

    @Override
    public String getRule() {
        return "If {this} is in your opening hand and you're not the starting player, you may begin the game with {this} on the battlefield with a luck counter on it. If you do, exile a card from your hand.";
    }

    @Override
    public boolean askUseOpeningHandAction(Card card, Player player, Game game) {
        return player.chooseUse(Outcome.PutCardInPlay, "Put " + card.getIdName() + " onto the battlefield?", this, game);
    }

    @Override
    public boolean isOpeningHandActionAllowed(Card card, Player player, Game game) {
        return !player.getId().equals(game.getStartingPlayerId());
    }

    @Override
    public void doOpeningHandAction(Card card, Player player, Game game) {
        this.resolve(game);
    }

}

class GemstoneCavernsEffect extends OneShotEffect {

    GemstoneCavernsEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may begin the game with {this} on the battlefield with a luck counter on it. If you do, exile a card from your hand";
    }

    GemstoneCavernsEffect(final GemstoneCavernsEffect effect) {
        super(effect);
    }

    @Override
    public GemstoneCavernsEffect copy() {
        return new GemstoneCavernsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                ContinuousEffect effect = new EntersBattlefieldEffect(new AddCountersSourceEffect(CounterType.LUCK.createInstance()), "");
                effect.setDuration(Duration.OneUse);
                game.addEffect(effect, source);
                if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        Cost cost = new ExileFromHandCost(new TargetCardInHand());
                        if (cost.canPay(source, source, source.getControllerId(), game)) {
                            result = cost.pay(source, game, source, source.getControllerId(), true, null);
                        }
                    }
                }
            }
        }
        return result;
    }
}
