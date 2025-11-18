package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AzulaCunningUsurper extends CardImpl {

    public AzulaCunningUsurper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Firebending 2
        this.addAbility(new FirebendingAbility(2));

        // When Azula enters, target opponent exiles a nontoken creature they control, then they exile a nonland card from their graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AzulaCunningUsurperExileEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // During your turn, you may cast cards exiled with Azula and you may cast them as though they had flash. Mana of any type can be spent to cast those spells.
        ability = new SimpleStaticAbility(new AzulaCunningUsurperCastEffect());
        ability.addEffect(new AzulaCunningUsurperFlashEffect());
        ability.addEffect(new AzulaCunningUsurperManaEffect());
        this.addAbility(ability);
    }

    private AzulaCunningUsurper(final AzulaCunningUsurper card) {
        super(card);
    }

    @Override
    public AzulaCunningUsurper copy() {
        return new AzulaCunningUsurper(this);
    }
}

class AzulaCunningUsurperExileEffect extends OneShotEffect {

    AzulaCunningUsurperExileEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent exiles a nontoken creature they control, " +
                "then they exile a nonland card from their graveyard";
    }

    private AzulaCunningUsurperExileEffect(final AzulaCunningUsurperExileEffect effect) {
        super(effect);
    }

    @Override
    public AzulaCunningUsurperExileEffect copy() {
        return new AzulaCunningUsurperExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        if (game.getBattlefield().contains(StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN, player.getId(), source, game, 1)) {
            TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN);
            target.withNotTarget(true);
            player.choose(Outcome.Exile, target, source, game);
            cards.add(target.getFirstTarget());
        }
        if (player.getGraveyard().count(StaticFilters.FILTER_CARD_A_NON_CREATURE, game) > 0) {
            TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_A_NON_CREATURE);
            target.withNotTarget(true);
            player.choose(Outcome.Exile, target, source, game);
            cards.add(target.getFirstTarget());
        }
        return player.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceLogName(game, source)
        );
    }
}

class AzulaCunningUsurperCastEffect extends AsThoughEffectImpl {

    AzulaCunningUsurperCastEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "during your turn, you may cast cards exiled with {this}";
    }

    private AzulaCunningUsurperCastEffect(final AzulaCunningUsurperCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AzulaCunningUsurperCastEffect copy() {
        return new AzulaCunningUsurperCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId) || !game.isActivePlayer(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        if (card == null || card.isLand(game)) {
            return false;
        }
        ExileZone exileZone = game.getState().getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return exileZone != null && exileZone.contains(objectId);
    }
}

class AzulaCunningUsurperFlashEffect extends AsThoughEffectImpl {

    AzulaCunningUsurperFlashEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "and you may cast them as though they had flash";
    }

    private AzulaCunningUsurperFlashEffect(final AzulaCunningUsurperFlashEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AzulaCunningUsurperFlashEffect copy() {
        return new AzulaCunningUsurperFlashEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId) || !game.isActivePlayer(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        if (card == null || card.isLand(game)) {
            return false;
        }
        ExileZone exileZone = game.getState().getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return exileZone != null && exileZone.contains(objectId);
    }
}

class AzulaCunningUsurperManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    AzulaCunningUsurperManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Mana of any type can be spent to cast those spells";
    }

    private AzulaCunningUsurperManaEffect(final AzulaCunningUsurperManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AzulaCunningUsurperManaEffect copy() {
        return new AzulaCunningUsurperManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId) || !game.isActivePlayer(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        if (card == null || card.isLand(game)) {
            return false;
        }
        ExileZone exileZone = game.getState().getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return exileZone != null && exileZone.contains(objectId);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
