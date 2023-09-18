package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author nantuko
 */
public final class CivilizedScholar extends CardImpl {

    public CivilizedScholar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.secondSideCardClazz = mage.cards.h.HomicidalBrute.class;

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {tap}: Draw a card, then discard a card. If a creature card is discarded this way, untap Civilized Scholar, then transform it.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CivilizedScholarEffect(), new TapSourceCost()));
        this.addAbility(new TransformAbility());
    }

    private CivilizedScholar(final CivilizedScholar card) {
        super(card);
    }

    @Override
    public CivilizedScholar copy() {
        return new CivilizedScholar(this);
    }
}



class CivilizedScholarEffect extends OneShotEffect {

    public CivilizedScholarEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw a card, then discard a card. If a creature card is discarded this way, untap {this}, then transform it";
    }

    private CivilizedScholarEffect(final CivilizedScholarEffect effect) {
        super(effect);
    }

    @Override
    public CivilizedScholarEffect copy() {
        return new CivilizedScholarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(1, source, game);
            Card card = player.discardOne(false, false, source, game);
            if (card != null && card.isCreature(game)) {
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    permanent.untap(game);
                    permanent.transform(source, game);
                }
            }
            return true;
        }
        return false;
    }
}
