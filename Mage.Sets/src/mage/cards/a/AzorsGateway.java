
package mage.cards.a;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public final class AzorsGateway extends CardImpl {

    public AzorsGateway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.addSuperType(SuperType.LEGENDARY);
        this.secondSideCardClazz = mage.cards.s.SanctumOfTheSun.class;

        // {1}, {T}: Draw a card, then exile a card from your hand. If cards with five or more different converted mana costs are exiled with Azor's Gateway, you gain 5 life, untap Azor's Gateway, and transform it.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AzorsGatewayEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private AzorsGateway(final AzorsGateway card) {
        super(card);
    }

    @Override
    public AzorsGateway copy() {
        return new AzorsGateway(this);
    }
}

class AzorsGatewayEffect extends OneShotEffect {

    public AzorsGatewayEffect() {
        super(Outcome.Benefit);
        this.staticText = "Draw a card, then exile a card from your hand. If cards with five or more different mana values are exiled with {this}, you gain 5 life, untap Azor's Gateway, and transform it";
    }

    public AzorsGatewayEffect(final AzorsGatewayEffect effect) {
        super(effect);
    }

    @Override
    public AzorsGatewayEffect copy() {
        return new AzorsGatewayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID exileId = CardUtil.getCardExileZoneId(game, source);
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && exileId != null && sourceObject != null) {
            controller.drawCards(1, source, game);
            TargetCardInHand target = new TargetCardInHand();
            controller.choose(outcome, target, source.getSourceId(), game);
            Card cardToExile = game.getCard(target.getFirstTarget());
            if (cardToExile != null) {
                controller.moveCardsToExile(cardToExile, source, game, true, exileId, sourceObject.getIdName());
            }
            Set<Integer> usedCMC = new HashSet<>();
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            if (exileZone != null) {
                for (Card card : exileZone.getCards(game)) {
                    usedCMC.add(card.getManaValue());
                }
                if (usedCMC.size() > 4) {
                    controller.gainLife(4, game, source);
                    new UntapSourceEffect().apply(game, source);
                    new TransformSourceEffect().apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
