package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SelflessExorcist extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card from a graveyard");

    public SelflessExorcist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {tap}: Exile target creature card from a graveyard. That card deals damage equal to its power to Selfless Exorcist.
        Ability ability = new SimpleActivatedAbility(new SelflessExorcistEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    private SelflessExorcist(final SelflessExorcist card) {
        super(card);
    }

    @Override
    public SelflessExorcist copy() {
        return new SelflessExorcist(this);
    }
}

class SelflessExorcistEffect extends OneShotEffect {

    SelflessExorcistEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature card from a graveyard. That card deals damage equal to its power to {this}";
    }

    private SelflessExorcistEffect(final SelflessExorcistEffect effect) {
        super(effect);
    }

    @Override
    public SelflessExorcistEffect copy() {
        return new SelflessExorcistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        game.getState().processAction(game);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return true;
        }
        permanent.damage(card.getPower().getValue(), card.getId(), source, game);
        return true;
    }
}
