package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HamaTheBloodbender extends CardImpl {

    public HamaTheBloodbender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/B}{U/B}{U/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Hama enters, target opponent mills three cards. Exile up to one noncreature, nonland card from that player's graveyard. For as long as you control Hama, you may cast the exiled card during your turn by waterbending {X} rather than paying its mana cost, where X is its mana value.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsTargetEffect(3));
        ability.addEffect(new HamaTheBloodbenderExileEffect());
        this.addAbility(ability);
    }

    private HamaTheBloodbender(final HamaTheBloodbender card) {
        super(card);
    }

    @Override
    public HamaTheBloodbender copy() {
        return new HamaTheBloodbender(this);
    }
}

class HamaTheBloodbenderExileEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("noncreature, nonland card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    HamaTheBloodbenderExileEffect() {
        super(Outcome.Benefit);
        staticText = "Exile up to one noncreature, nonland card from that player's graveyard. " +
                "For as long as you control {this}, you may cast the exiled card during your turn " +
                "by waterbending {X} rather than paying its mana cost, where X is its mana value.";
    }

    private HamaTheBloodbenderExileEffect(final HamaTheBloodbenderExileEffect effect) {
        super(effect);
    }

    @Override
    public HamaTheBloodbenderExileEffect copy() {
        return new HamaTheBloodbenderExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        TargetCard target = new TargetCardInGraveyard(0, 1, filter, true);
        controller.choose(Outcome.PlayForFree, opponent.getGraveyard(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        controller.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        if (source.getSourcePermanentIfItStillExists(game) != null) {
            game.addEffect(new HamaTheBloodbenderCastEffect(card, game), source);
        }
        return true;
    }
}

class HamaTheBloodbenderCastEffect extends AsThoughEffectImpl {

    HamaTheBloodbenderCastEffect(Card card, Game game) {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileControlled, Outcome.AIDontUseIt);
        this.setTargetPointer(new FixedTarget(card, game));
    }

    private HamaTheBloodbenderCastEffect(final HamaTheBloodbenderCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HamaTheBloodbenderCastEffect copy() {
        return new HamaTheBloodbenderCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!game.isActivePlayer(affectedControllerId) || !source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            discard();
            return false;
        }
        if (!card.getId().equals(objectId)) {
            return false;
        }
        Player player = game.getPlayer(affectedControllerId);
        if (player == null) {
            return false;
        }
        Costs<Cost> newCosts = new CostsImpl<>();
        newCosts.add(new WaterbendCost(card.getManaValue()));
        newCosts.addAll(card.getSpellAbility().getCosts());
        player.setCastSourceIdWithAlternateMana(card.getId(), null, newCosts);
        return true;
    }
}
