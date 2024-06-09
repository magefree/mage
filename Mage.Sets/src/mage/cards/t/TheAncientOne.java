package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessConditionSourceEffect;
import mage.abilities.hint.StaticHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TheAncientOne extends CardImpl {

    public TheAncientOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Descend 8 -- The Ancient One can't attack or block unless there are eight or more permanents in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                new CantAttackBlockUnlessConditionSourceEffect(DescendCondition.EIGHT)
        ).addHint(DescendCondition.getHint()).setAbilityWord(AbilityWord.DESCEND_8));

        // {2}{U}{B}: Draw a card, then discard a card. When you discard a card this way, target player mills cards equal to its mana value.
        this.addAbility(new SimpleActivatedAbility(
                new TheAncientOneEffect(),
                new ManaCostsImpl<>("{2}{U}{B}")
        ));
    }

    private TheAncientOne(final TheAncientOne card) {
        super(card);
    }

    @Override
    public TheAncientOne copy() {
        return new TheAncientOne(this);
    }
}

class TheAncientOneEffect extends OneShotEffect {

    TheAncientOneEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card, then discard a card. "
                + "When you discard a card this way, target player mills cards equal to its mana value";
    }

    private TheAncientOneEffect(final TheAncientOneEffect effect) {
        super(effect);
    }

    @Override
    public TheAncientOneEffect copy() {
        return new TheAncientOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.drawCards(1, source, game);
        Card discarded = controller.discardOne(false, false, source, game);
        if (discarded == null) {
            return true;
        }

        int amount = discarded.getManaValue();
        ReflexiveTriggeredAbility trigger = new ReflexiveTriggeredAbility(
                new MillCardsTargetEffect(amount)
                        .setText("target player mills cards equal to its mana value"),
                false
        );
        trigger.addHint(new StaticHint("Mana value of the discarded card: " + amount));
        trigger.addTarget(new TargetPlayer());
        trigger.setTriggerPhrase("When you discard a card this way, ");
        game.fireReflexiveTriggeredAbility(trigger, source);
        return true;
    }

}