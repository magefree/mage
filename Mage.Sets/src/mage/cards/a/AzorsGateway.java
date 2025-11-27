package mage.cards.a;

import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
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
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AzorsGateway extends TransformingDoubleFacedCard {

    public AzorsGateway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}",
                "Sanctum of the Sun",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, "");

        // {1}, {T}: Draw a card, then exile a card from your hand.
        //           If cards with five or more different converted mana costs are exiled with Azor's Gateway,
        //           you gain 5 life, untap Azor's Gateway, and transform it.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new AzorsGatewayEffect());
        ability.addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(5), AzorsGatewayCondition.instance, "If cards with five or more " +
                "different mana values are exiled with {this}, you gain 5 life, untap {this}, and transform it."
        ).addEffect(new UntapSourceEffect()).addEffect(new TransformSourceEffect()));
        this.getLeftHalfCard().addAbility(ability);

        // Sanctum of the Sun
        // {T}: Add X mana of any one color, where X is your life total.
        this.getRightHalfCard().addAbility(new DynamicManaAbility(new Mana(0, 0, 0, 0, 0, 0, 1, 0), ControllerLifeCount.instance, new TapSourceCost(),
                "Add X mana of any one color, where X is your life total", true));

    }

    private AzorsGateway(final AzorsGateway card) {
        super(card);
    }

    @Override
    public AzorsGateway copy() {
        return new AzorsGateway(this);
    }
}

enum AzorsGatewayCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return exileZone != null
                && exileZone
                .getCards(game)
                .stream()
                .map(MageObject::getManaValue)
                .distinct()
                .mapToInt(x -> 1)
                .sum() >= 5;
    }
}

class AzorsGatewayEffect extends OneShotEffect {

    AzorsGatewayEffect() {
        super(Outcome.Benefit);
        this.staticText = ", then exile a card from your hand";
    }

    private AzorsGatewayEffect(final AzorsGatewayEffect effect) {
        super(effect);
    }

    @Override
    public AzorsGatewayEffect copy() {
        return new AzorsGatewayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInHand();
        target.withChooseHint("to exile");
        player.choose(outcome, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceLogName(game, source)
        );
    }
}
