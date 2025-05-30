package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScholarOfCombustion extends CardImpl {

    public ScholarOfCombustion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Scholar of Combustion enters, exile up to one target instant or sorcery card from your graveyard. You may cast that card until the end of your next turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ScholarOfCombustionEffect());
        ability.addTarget(new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD
        ));
        this.addAbility(ability);
    }

    private ScholarOfCombustion(final ScholarOfCombustion card) {
        super(card);
    }

    @Override
    public ScholarOfCombustion copy() {
        return new ScholarOfCombustion(this);
    }
}

class ScholarOfCombustionEffect extends OneShotEffect {

    ScholarOfCombustionEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target instant or sorcery card from your graveyard. " +
                "You may cast that card until the end of your next turn";
    }

    private ScholarOfCombustionEffect(final ScholarOfCombustionEffect effect) {
        super(effect);
    }

    @Override
    public ScholarOfCombustionEffect copy() {
        return new ScholarOfCombustionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        CardUtil.makeCardPlayable(
                game, source, card, true,
                Duration.UntilEndOfYourNextTurn, false
        );
        return true;
    }
}
