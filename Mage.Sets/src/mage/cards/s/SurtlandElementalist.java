package mage.cards.s;

import java.util.UUID;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author weirddan455
 */
public final class SurtlandElementalist extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Giant card from your hand");
    static {
        filter.add(SubType.GIANT.getPredicate());
    }

    public SurtlandElementalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // As an additional cost to cast this spell, reveal a Giant card from your hand or pay {2}.
        this.getSpellAbility().addCost(new OrCost(
                new RevealTargetFromHandCost(new TargetCardInHand(filter)),
                new GenericManaCost(2),
                "reveal a Giant card from your hand or pay {2}"));

        // Whenever Surtland Elementalist attacks, you may cast an instant or sorcery spell from your hand without paying its mana cost.
        this.addAbility(new AttacksTriggeredAbility(new SurtlandElementalistEffect(), true));
    }

    private SurtlandElementalist(final SurtlandElementalist card) {
        super(card);
    }

    @Override
    public SurtlandElementalist copy() {
        return new SurtlandElementalist(this);
    }
}

class SurtlandElementalistEffect extends OneShotEffect {

    public SurtlandElementalistEffect () {
        super(Outcome.PlayForFree);
        this.staticText = "cast an instant or sorcery spell from your hand without paying its mana cost";
    }

    private SurtlandElementalistEffect(final SurtlandElementalistEffect effect) {
        super(effect);
    }

    @Override
    public SurtlandElementalistEffect copy() {
        return new SurtlandElementalistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetCardInHand target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);
            if (player.chooseTarget(Outcome.PlayForFree, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                    boolean cardWasCast = player.cast(player.chooseAbilityForCast(card, game, true),
                            game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                    return cardWasCast;
                }
            }
        }
        return false;
    }
}
