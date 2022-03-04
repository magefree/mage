package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BodyOfKnowledge extends CardImpl {

    public BodyOfKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Body of Knowledge's power and toughness are each equal to the number of cards in your hand.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetPowerToughnessSourceEffect(
                        CardsInControllerHandCount.instance, Duration.EndOfGame
                )
        ));

        // You have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // Whenever Body of Knowledge is dealt damage, draw that many cards.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new BodyOfKnowledgeEffect(), false, false
        ));
    }

    private BodyOfKnowledge(final BodyOfKnowledge card) {
        super(card);
    }

    @Override
    public BodyOfKnowledge copy() {
        return new BodyOfKnowledge(this);
    }
}

class BodyOfKnowledgeEffect extends OneShotEffect {

    BodyOfKnowledgeEffect() {
        super(Outcome.Benefit);
        staticText = "draw that many cards";
    }

    private BodyOfKnowledgeEffect(final BodyOfKnowledgeEffect effect) {
        super(effect);
    }

    @Override
    public BodyOfKnowledgeEffect copy() {
        return new BodyOfKnowledgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        Player player = game.getPlayer(source.getControllerId());
        return player != null
                && amount > 0
                && player.drawCards(amount, source, game) > 0;
    }
}
