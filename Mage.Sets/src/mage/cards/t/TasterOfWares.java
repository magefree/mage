package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TasterOfWares extends CardImpl {

    public TasterOfWares(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters, target opponent reveals X cards from their hand, where X is the number of Goblins you control. You choose one of those cards. That player exiles it. If an instant or sorcery card is exiled this way, you may cast it for as long as you control this creature, and mana of any type can be spent to cast that spell.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TasterOfWaresEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability.addHint(TasterOfWaresEffect.getHint()));
    }

    private TasterOfWares(final TasterOfWares card) {
        super(card);
    }

    @Override
    public TasterOfWares copy() {
        return new TasterOfWares(this);
    }
}

class TasterOfWaresEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GOBLIN);
    private static final Hint hint = new ValueHint("Goblins you control", new PermanentsOnBattlefieldCount(filter));

    public static Hint getHint() {
        return hint;
    }

    TasterOfWaresEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent reveals X cards from their hand, where X is the number of Goblins you control. " +
                "You choose one of those cards. That player exiles it. If an instant or sorcery card " +
                "is exiled this way, you may cast it for as long as you control this creature, " +
                "and mana of any type can be spent to cast that spell.";
    }

    private TasterOfWaresEffect(final TasterOfWaresEffect effect) {
        super(effect);
    }

    @Override
    public TasterOfWaresEffect copy() {
        return new TasterOfWaresEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null || opponent.getHand().isEmpty()) {
            return false;
        }
        int count = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        if (count < 1) {
            return false;
        }
        TargetCard target = new TargetCardInHand(Integer.min(opponent.getHand().size(), count), StaticFilters.FILTER_CARD);
        target.withChooseHint("to reveal");
        opponent.choose(outcome, opponent.getHand(), target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        opponent.revealCards(source, cards, game);
        Card card;
        switch (cards.size()) {
            case 0:
                return false;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                TargetCard targetCard = new TargetCardInHand(1, StaticFilters.FILTER_CARD);
                targetCard.withChooseHint("to exile");
                controller.choose(outcome, cards, target, source, game);
                card = game.getCard(targetCard.getFirstTarget());
        }
        if (card == null) {
            return false;
        }
        opponent.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        if (!card.isInstantOrSorcery(game)) {
            return true;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null && permanent.isControlledBy(source.getControllerId())) {
            CardUtil.makeCardPlayable(game, source, card, true, Duration.WhileControlled, true);
        }
        return true;
    }
}
