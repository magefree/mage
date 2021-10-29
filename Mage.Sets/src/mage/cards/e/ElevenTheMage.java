package mage.cards.e;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.cost.CastWithoutPayingManaCostEffect;
import mage.abilities.keyword.FriendsForeverAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElevenTheMage extends CardImpl {

    public ElevenTheMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Your maximum hand size is eleven.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                11, Duration.WhileOnBattlefield, MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // Whenever Eleven, the Mage attacks, you draw a card and you lose 1 life. Then if you have eleven or more cards in your hand, you may cast an instant or sorcery spell from your hand without paying its mana cost.
        this.addAbility(new AttacksTriggeredAbility(new ElevenTheMageEffect()));

        // Friends forever
        this.addAbility(FriendsForeverAbility.getInstance());
    }

    private ElevenTheMage(final ElevenTheMage card) {
        super(card);
    }

    @Override
    public ElevenTheMage copy() {
        return new ElevenTheMage(this);
    }
}

class ElevenTheMageEffect extends OneShotEffect {

    ElevenTheMageEffect() {
        super(Outcome.Benefit);
        staticText = "you draw a card and you lose 1 life. Then if you have eleven or more cards in your hand, " +
                "you may cast an instant or sorcery spell from your hand without paying its mana cost";
    }

    private ElevenTheMageEffect(final ElevenTheMageEffect effect) {
        super(effect);
    }

    @Override
    public ElevenTheMageEffect copy() {
        return new ElevenTheMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(1, source, game);
        player.loseLife(1, game, source, false);
        if (player.getHand().size() < 11) {
            return true;
        }
        // TODO: change this to fit with changes made in https://github.com/magefree/mage/pull/8136 when merged
        Target target = new TargetCardInHand(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);
        if (!target.canChoose(
                source.getSourceId(), player.getId(), game
        ) || !player.chooseUse(
                Outcome.PlayForFree, "Cast an instant or sorcery spell " +
                        "from your hand without paying its mana cost?", source, game
        )) {
            return true;
        }
        Card cardToCast = null;
        boolean cancel = false;
        while (player.canRespond()
                && !cancel) {
            if (player.chooseTarget(Outcome.PlayForFree, target, source, game)) {
                cardToCast = game.getCard(target.getFirstTarget());
                if (cardToCast != null) {
                    if (cardToCast.getSpellAbility() == null) {
                        Logger.getLogger(CastWithoutPayingManaCostEffect.class).fatal("Card: "
                                + cardToCast.getName() + " is no land and has no spell ability!");
                        cancel = true;
                    }
                    if (cardToCast.getSpellAbility().canChooseTarget(game, player.getId())) {
                        cancel = true;
                    }
                }
            } else {
                cancel = true;
            }
        }
        if (cardToCast != null) {
            game.getState().setValue("PlayFromNotOwnHandZone" + cardToCast.getId(), Boolean.TRUE);
            player.cast(player.chooseAbilityForCast(cardToCast, game, true),
                    game, true, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + cardToCast.getId(), null);
        }
        return true;
    }
}
