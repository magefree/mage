
package mage.cards.k;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LookAtCardsExiledWithThisEffect;
import mage.abilities.effects.common.continuous.MayPlayCardsExiledWithThisEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class KheruMindEater extends CardImpl {

    public KheruMindEater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Kheru Mind-Eater deals combat damage to a player, that player exiles a card from their hand face down.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new KheruMindEaterExileEffect(), false, true));

        // You may look at and play cards exiled with Kheru Mind-Eater.
        this.addAbility(new SimpleStaticAbility(new MayPlayCardsExiledWithThisEffect()));
        this.addAbility(new SimpleStaticAbility(new LookAtCardsExiledWithThisEffect()));
    }

    private KheruMindEater(final KheruMindEater card) {
        super(card);
    }

    @Override
    public KheruMindEater copy() {
        return new KheruMindEater(this);
    }
}

class KheruMindEaterExileEffect extends OneShotEffect {

    KheruMindEaterExileEffect() {
        super(Outcome.Discard);
        staticText = "that player exiles a card of their hand face down";
    }

    private KheruMindEaterExileEffect(final KheruMindEaterExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player != null && !player.getHand().isEmpty()) {
            Target target = new TargetCardInHand(1, new FilterCard());
            target.chooseTarget(Outcome.Exile, player.getId(), source, game);
            Card card = game.getCard(target.getFirstTarget());
            MageObject sourceObject = game.getObject(source);
            if (card != null && sourceObject != null) {
                return CardUtil.moveCardsToExileFaceDown(game, source, controller, card, true);
            }
        }
        return false;
    }

    @Override
    public KheruMindEaterExileEffect copy() {
        return new KheruMindEaterExileEffect(this);
    }
}