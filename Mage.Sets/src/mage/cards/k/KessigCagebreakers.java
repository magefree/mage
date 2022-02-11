package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.WolfToken;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class KessigCagebreakers extends CardImpl {

    public KessigCagebreakers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Kessig Cagebreakers attacks, create a 2/2 green Wolf creature token tapped and attacking for each creature card in your graveyard.
        this.addAbility(new AttacksTriggeredAbility(new KessigCagebreakersEffect(), false));
    }

    private KessigCagebreakers(final KessigCagebreakers card) {
        super(card);
    }

    @Override
    public KessigCagebreakers copy() {
        return new KessigCagebreakers(this);
    }
}

class KessigCagebreakersEffect extends OneShotEffect {

    public KessigCagebreakersEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a 2/2 green Wolf creature token that's tapped and attacking for each creature card in your graveyard";
    }

    public KessigCagebreakersEffect(final KessigCagebreakersEffect effect) {
        super(effect);
    }

    @Override
    public KessigCagebreakersEffect copy() {
        return new KessigCagebreakersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            WolfToken token = new WolfToken();
            int count = player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game);
            for (int i = 0; i < count; i++) {
                token.putOntoBattlefield(1, game, source, source.getControllerId(), true, true);
            }
            return true;
        }
        return false;
    }
}
