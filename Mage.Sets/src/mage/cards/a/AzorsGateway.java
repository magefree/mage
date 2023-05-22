
package mage.cards.a;

import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AzorsGateway extends TransformingDoubleFacedCard {

    public AzorsGateway(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}",
                "Sanctum of the Sun",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // {1}, {T}: Draw a card, then exile a card from your hand. If cards with five or more different converted mana costs are exiled with Azor's Gateway, you gain 5 life, untap Azor's Gateway, and transform it.
        Ability ability = new SimpleActivatedAbility(new AzorsGatewayEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);

        // Sanctum of the Sun
        // <i>(Transforms from Azor's Gateway.)</i>
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
                new InfoEffect("<i>(Transforms from Azor's Gateway.)</i>")
        ).setRuleAtTheTop(true));

        // {T}: Add X mana of any one color, where X is your life total.
        this.getRightHalfCard().addAbility(new DynamicManaAbility(
                Mana.AnyMana(1), ControllerLifeCount.instance, new TapSourceCost(),
                "Add X mana of any one color, where X is is your life total", true
        ));
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
        this.staticText = "Draw a card, then exile a card from your hand. " +
                "If cards with five or more different mana values are exiled with {this}, " +
                "you gain 5 life, untap Azor's Gateway, and transform it";
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
        if (controller == null) {
            return false;
        }

        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }

        UUID exileId = CardUtil.getCardExileZoneId(game, source);

        controller.drawCards(1, source, game);
        TargetCardInHand target = new TargetCardInHand();
        controller.choose(outcome, target, source, game);
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
}
