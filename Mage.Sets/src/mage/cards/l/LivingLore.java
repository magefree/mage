package mage.cards.l;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LivingLore extends CardImpl {

    public LivingLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Living Lore enters the battlefield, exile an instant or sorcery card from your graveyard.
        this.addAbility(new AsEntersBattlefieldAbility(
                new LivingLoreExileEffect(), "exile an instant or sorcery card from your graveyard"
        ));

        // Living Lore's power and toughness are each equal to the exiled card's converted mana cost.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(LivingLoreValue.instance)
                .setText("{this}'s power and toughness are each equal to the exiled card's mana value")
        ));

        // Whenever Living Lore deals combat damage, you may sacrifice it. If you do, 
        // you may cast the exiled card without paying its mana cost.
        this.addAbility(new DealsCombatDamageTriggeredAbility(new DoIfCostPaid(
                new LivingLoreCastEffect(), new SacrificeSourceCost().setText("sacrifice it")
        ), false));
    }

    private LivingLore(final LivingLore card) {
        super(card);
    }

    @Override
    public LivingLore copy() {
        return new LivingLore(this);
    }
}

class LivingLoreExileEffect extends OneShotEffect {

    public LivingLoreExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile an instant or sorcery card from your graveyard";
    }

    public LivingLoreExileEffect(final LivingLoreExileEffect effect) {
        super(effect);
    }

    @Override
    public LivingLoreExileEffect copy() {
        return new LivingLoreExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD
        );
        target.setNotTarget(true);
        controller.chooseTarget(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        controller.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(game, source, 1),
                CardUtil.getSourceName(game, source)
        );
        return true;
    }
}

enum LivingLoreValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int offset = game.getPermanent(sourceAbility.getSourceId()) != null ? 1 : 0;
        ExileZone exileZone = game
                .getExile()
                .getExileZone(CardUtil.getExileZoneId(game, sourceAbility, offset));
        if (exileZone == null) {
            return 0;
        }
        return exileZone
                .getCards(game)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .sum();
    }

    @Override
    public LivingLoreValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class LivingLoreCastEffect extends OneShotEffect {

    LivingLoreCastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast the exiled card without paying its mana cost";
    }

    public LivingLoreCastEffect(final LivingLoreCastEffect effect) {
        super(effect);
    }

    @Override
    public LivingLoreCastEffect copy() {
        return new LivingLoreCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source, -2));
        if (controller == null || exileZone == null || exileZone.isEmpty()) {
            return false;
        }

        return CardUtil.castSpellWithAttributesForFree(controller, source, game,
                new CardsImpl(exileZone), StaticFilters.FILTER_CARD);
    }
}
