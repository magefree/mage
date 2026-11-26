package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author gravitybone
 * @author Aquid
 */
public final class AnrakyrTheTraveller extends CardImpl {

    public AnrakyrTheTraveller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lord of the Pyrrhian Legions — Whenever Anrakyr the Traveller attacks, you may cast an artifact spell from your hand or graveyard by paying life equal to its mana value rather than paying its mana cost.
        Ability ability = new AttacksTriggeredAbility(new AnrakyrTheTravellerEffect(), true);

        this.addAbility(ability.withFlavorWord("Lord of the Pyrrhian Legions"));
    }

    private AnrakyrTheTraveller(final AnrakyrTheTraveller card) {
        super(card);
    }

    @Override
    public AnrakyrTheTraveller copy() {
        return new AnrakyrTheTraveller(this);
    }
}

class AnrakyrTheTravellerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterArtifactCard("an artifact spell");

    AnrakyrTheTravellerEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "you may cast " + filter.getMessage() + " from your hand or graveyard by paying life equal to its mana value rather than paying its mana cost.";
    }

    private AnrakyrTheTravellerEffect(final AnrakyrTheTravellerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Set<Card> cards = player.getHand().getCards(filter, source.getControllerId(), source, game);
        cards.addAll(player.getGraveyard().getCards(filter, source.getControllerId(), source, game));

        return CardUtil.castSpellWithAttributesForCost(player, source, game, new CardsImpl(cards), filter,
                "Cast spell by paying life equal to its mana value rather than paying its mana cost",
                faceCard -> new PayLifeCost(faceCard.getManaValue()));
    }

    @Override
    public AnrakyrTheTravellerEffect copy() {
        return new AnrakyrTheTravellerEffect(this);
    }
}
