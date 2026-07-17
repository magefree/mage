package mage.cards.t;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class TabletOfDiscovery extends CardImpl {

    public TabletOfDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        // When this artifact enters, mill a card. You may play that card this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TabletOfDiscoveryEffect()));

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {T}: Add {R}{R}. Spend this mana only to cast instant and sorcery spells.
        Ability ability = new ConditionalColoredManaAbility(new Mana(0, 0, 0, 2, 0, 0, 0, 0), new InstantOrSorcerySpellManaBuilder());
        this.addAbility(ability);
    }

    private TabletOfDiscovery(final TabletOfDiscovery card) {
        super(card);
    }

    @Override
    public TabletOfDiscovery copy() {
        return new TabletOfDiscovery(this);
    }
}

class TabletOfDiscoveryEffect extends OneShotEffect {

    TabletOfDiscoveryEffect() {
        super(Outcome.Benefit);
        staticText = "mill a card. You may play that card this turn";
    }

    private TabletOfDiscoveryEffect(final TabletOfDiscoveryEffect effect) {
        super(effect);
    }

    @Override
    public TabletOfDiscoveryEffect copy() {
        return new TabletOfDiscoveryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(1, source, game);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, false, Duration.EndOfTurn, false);
        }
        return true;
    }
}
