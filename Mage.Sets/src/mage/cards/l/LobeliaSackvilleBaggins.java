package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LobeliaSackvilleBaggins extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card from an opponent's graveyard that was put there from the battlefield this turn"
    );

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    public LobeliaSackvilleBaggins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Lobelia Sackville-Baggins enters the battlefield, exile target creature card from an opponent's graveyard that was put there from the battlefield this turn, then create X Treasure tokens, where X is the exiled card's power.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LobeliaSackvilleBagginsEffect());
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter));
        this.addAbility(ability, new CardsPutIntoGraveyardWatcher());
    }

    private LobeliaSackvilleBaggins(final LobeliaSackvilleBaggins card) {
        super(card);
    }

    @Override
    public LobeliaSackvilleBaggins copy() {
        return new LobeliaSackvilleBaggins(this);
    }
}

class LobeliaSackvilleBagginsEffect extends OneShotEffect {

    LobeliaSackvilleBagginsEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature card from an opponent's graveyard that was put there " +
                "from the battlefield this turn, then create X Treasure tokens, where X is the exiled card's power";
    }

    private LobeliaSackvilleBagginsEffect(final LobeliaSackvilleBagginsEffect effect) {
        super(effect);
    }

    @Override
    public LobeliaSackvilleBagginsEffect copy() {
        return new LobeliaSackvilleBagginsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        int power = card.getPower().getValue();
        if (power > 0) {
            new TreasureToken().putOntoBattlefield(power, game, source);
        }
        return true;
    }
}
