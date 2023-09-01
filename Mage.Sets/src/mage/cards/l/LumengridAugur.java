
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
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
import mage.target.TargetPlayer;

/**
 *
 * @author L_J
 */
public final class LumengridAugur extends CardImpl {

    public LumengridAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}, {T}: Target player draws a card, then discards a card. If that player discards an artifact card this way, untap Lumengrid Augur.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LumengridAugurEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private LumengridAugur(final LumengridAugur card) {
        super(card);
    }

    @Override
    public LumengridAugur copy() {
        return new LumengridAugur(this);
    }
}

class LumengridAugurEffect extends OneShotEffect {

    public LumengridAugurEffect() {
        super(Outcome.DrawCard);
        staticText = "Target player draws a card, then discards a card. If that player discards an artifact card this way, untap {this}";
    }

    private LumengridAugurEffect(final LumengridAugurEffect effect) {
        super(effect);
    }

    @Override
    public LumengridAugurEffect copy() {
        return new LumengridAugurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null) {
            player.drawCards(1, source, game);
            Card discardedCard = player.discardOne(false, false, source, game);
            if (discardedCard != null && discardedCard.isArtifact(game)) {
                if (sourcePermanent != null) {
                    sourcePermanent.untap(game);
                }
            }
            return true;
        }
        return false;
    }
}
