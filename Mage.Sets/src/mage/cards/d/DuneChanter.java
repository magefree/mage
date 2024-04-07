package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOwnedCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class DuneChanter extends CardImpl {

    public DuneChanter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Lands you control and land cards you own that aren't on the battlefield are Deserts in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new DuneChanterContinuousEffect()));

        // Lands you control have "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new AnyColorManaAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_LANDS, false
        )));

        // {T}: Mill two cards. You gain 1 life for each land card milled this way.
        this.addAbility(new SimpleActivatedAbility(new DuneChanterEffect(), new TapSourceCost()));
    }

    private DuneChanter(final DuneChanter card) {
        super(card);
    }

    @Override
    public DuneChanter copy() {
        return new DuneChanter(this);
    }
}

class DuneChanterContinuousEffect extends ContinuousEffectImpl {
    private static final FilterPermanent filterPermanent = StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS;
    private static final FilterOwnedCard filterCard = new FilterOwnedCard("land cards you own that aren't on the battlefield");
    private static final SubType subType = SubType.DESERT;

    static {
        filterCard.add(CardType.LAND.getPredicate());
    }

    public DuneChanterContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Lands you control and land cards you own that aren't on the battlefield are Deserts in addition to their other types";
    }

    private DuneChanterContinuousEffect(final DuneChanterContinuousEffect effect) {
        super(effect);
    }

    @Override
    public DuneChanterContinuousEffect copy() {
        return new DuneChanterContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }

        // lands cards you own that aren't on the battlefield
        // in graveyard
        for (UUID cardId : controller.getGraveyard()) {
            Card card = game.getCard(cardId);
            if (filterCard.match(card, controllerId, source, game) && !card.hasSubtype(subType, game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
            }
        }
        // on hand
        for (UUID cardId : controller.getHand()) {
            Card card = game.getCard(cardId);
            if (filterCard.match(card, controllerId, source, game) && !card.hasSubtype(subType, game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
            }
        }
        // in exile
        for (Card card : game.getState().getExile().getAllCards(game, controllerId)) {
            if (filterCard.match(card, controllerId, source, game) && !card.hasSubtype(subType, game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
            }
        }
        // in library
        for (Card card : controller.getLibrary().getCards(game)) {
            if (filterCard.match(card, controllerId, source, game) && !card.hasSubtype(subType, game)) {
                game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
            }
        }

        // lands you control
        List<Permanent> lands = game.getBattlefield().getAllActivePermanents(
                filterPermanent, controllerId, game);
        for (Permanent land : lands) {
            if (land != null) {
                land.addSubType(game, subType);
            }
        }
        return true;
    }
}

class DuneChanterEffect extends OneShotEffect {

    DuneChanterEffect() {
        super(Outcome.Benefit);
        staticText = "mill two cards. You gain 1 life for each land card milled this way.";
    }

    private DuneChanterEffect(final DuneChanterEffect effect) {
        super(effect);
    }

    @Override
    public DuneChanterEffect copy() {
        return new DuneChanterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int lifeToGain = player
                .millCards(2, source, game)
                .getCards(game)
                .stream()
                .filter(c -> c.isLand(game))
                .mapToInt(c -> game.getState().getZone(c.getId()) == Zone.GRAVEYARD ? 1 : 0)
                .sum();
        if (lifeToGain > 0) {
            new GainLifeEffect(lifeToGain).apply(game, source);
        }
        return true;
    }
}


