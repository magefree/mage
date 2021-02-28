package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class EaterOfTheDead extends CardImpl {

    public EaterOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {0}: If Eater of the Dead is tapped, exile target creature card from a graveyard and untap Eater of the Dead.
        Ability ability = new SimpleActivatedAbility(new EaterOfTheDeadEffect(), new GenericManaCost(0));
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(ability);
    }

    private EaterOfTheDead(final EaterOfTheDead card) {
        super(card);
    }

    @Override
    public EaterOfTheDead copy() {
        return new EaterOfTheDead(this);
    }
}

class EaterOfTheDeadEffect extends OneShotEffect {

    EaterOfTheDeadEffect() {
        super(Outcome.Exile);
        staticText = "If {this} is tapped, exile target creature card from a graveyard and untap {this}";
    }

    private EaterOfTheDeadEffect(final EaterOfTheDeadEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null || !permanent.isTapped()) {
            return false;
        }
        Card card = game.getCard(source.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.EXILED, source, game);
        }
        permanent.untap(game);
        return true;
    }

    @Override
    public EaterOfTheDeadEffect copy() {
        return new EaterOfTheDeadEffect(this);
    }
}
