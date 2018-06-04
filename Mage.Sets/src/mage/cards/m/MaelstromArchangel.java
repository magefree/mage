
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public final class MaelstromArchangel extends CardImpl {

    public MaelstromArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Maelstrom Archangel deals combat damage to a player, you may cast a nonland card from your hand without paying its mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new MaelstromArchangelCastEffect(), false));

    }

    public MaelstromArchangel(final MaelstromArchangel card) {
        super(card);
    }

    @Override
    public MaelstromArchangel copy() {
        return new MaelstromArchangel(this);
    }
}

class MaelstromArchangelCastEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterNonlandCard("nonland card from your hand");

    public MaelstromArchangelCastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast a nonland card from your hand without paying its mana cost";
    }

    public MaelstromArchangelCastEffect(final MaelstromArchangelCastEffect effect) {
        super(effect);
    }

    @Override
    public MaelstromArchangelCastEffect copy() {
        return new MaelstromArchangelCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInHand(filter);
            if (target.canChoose(source.getSourceId(), controller.getId(), game)
                    && controller.chooseUse(outcome, "Cast a nonland card from your hand without paying its mana cost?", source, game)) {
                Card cardToCast = null;
                boolean cancel = false;
                while (controller.canRespond() && !cancel) {
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        cardToCast = game.getCard(target.getFirstTarget());
                        if (cardToCast != null && cardToCast.getSpellAbility().canChooseTarget(game)) {
                            cancel = true;
                        }
                    } else {
                        cancel = true;
                    }
                }
                if (cardToCast != null) {
                    controller.cast(cardToCast.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                }
            }
            return true;
        }
        return false;
    }
}
