package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EscapesWithAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class SkywayRobber extends CardImpl {

    public SkywayRobber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.addSubType(SubType.BIRD, SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Escape—{3}{U}, Exile five other cards from your graveyard. (You may cast this card from your graveyard for its escape cost.)
        this.addAbility(new EscapeAbility(this, "{3}{U}", 5));

        // Skyway Robber escapes with “Whenever Skyway Robber deals combat damage to a player, you may cast an artifact, instant, or sorcery spell from among cards exiled with Skyway Robber without paying its mana cost.”
        // NOTE: Optional is handled by the effect, this way it won't prompt the player if no valid cards are available
        TriggeredAbility dealsDamageAbility = new DealsCombatDamageToAPlayerTriggeredAbility(new SkywayRobberCastForFreeEffect(), false);
        this.addAbility(new EscapesWithAbility(0, dealsDamageAbility));
    }

    private SkywayRobber(final SkywayRobber card) {
        super(card);
    }

    @Override
    public SkywayRobber copy() {
        return new SkywayRobber(this);
    }
}

class SkywayRobberCastForFreeEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("an artifact, instant, or sorcery card");
    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate())
        );
    }

    public SkywayRobberCastForFreeEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast an artifact, instant, or sorcery spell from among cards exiled with Skyway Robber without paying its mana cost";
    }

    private SkywayRobberCastForFreeEffect(final SkywayRobberCastForFreeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        String exileZoneName = CardUtil.getObjectZoneString(CardUtil.SOURCE_EXILE_ZONE_TEXT, source.getSourceId(), game, source.getSourceObjectZoneChangeCounter()-1, false);
        UUID exileId = CardUtil.getExileZoneId(exileZoneName, game);
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null) {
            return false;
        }

        Set<Card> possibleCards = exileZone.getCards(filter, game);
        if (possibleCards.isEmpty()) {
            return false;
        }

        boolean choseToPlay = controller.chooseUse(
                Outcome.PlayForFree,
                "Cast an artifact, instant, or sorcery spell from among cards exiled with Skyway Robber without paying its mana cost?",
                source,
                game);
        if (!choseToPlay) {
            return false;
        }

        TargetCardInExile target = new TargetCardInExile(filter, exileId);
        if (!controller.chooseTarget(Outcome.PlayForFree, target, source, game)) {
            return false;
        }

        Card chosenCard = game.getCard(target.getFirstTarget());
        if (chosenCard == null) {
            return false;
        }

        return CardUtil.castSpellWithAttributesForFree(controller, source, game, chosenCard);
    }

    @Override
    public SkywayRobberCastForFreeEffect copy() {
        return new SkywayRobberCastForFreeEffect(this);
    }
}