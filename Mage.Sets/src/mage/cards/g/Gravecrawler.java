
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;

/**
 *
 * @author BetaSteward
 */
public final class Gravecrawler extends CardImpl {

    public Gravecrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Gravecrawler can't block.
        this.addAbility(new CantBlockAbility());

        // You may cast Gravecrawler from your graveyard as long as you control a Zombie.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new GravecrawlerPlayEffect()));

    }

    private Gravecrawler(final Gravecrawler card) {
        super(card);
    }

    @Override
    public Gravecrawler copy() {
        return new Gravecrawler(this);
    }
}

class GravecrawlerPlayEffect extends AsThoughEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("zombie");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public GravecrawlerPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from your graveyard as long as you control a Zombie";
    }

    private GravecrawlerPlayEffect(final GravecrawlerPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GravecrawlerPlayEffect copy() {
        return new GravecrawlerPlayEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId()) && source.isControlledBy(affectedControllerId)) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                if (game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

}