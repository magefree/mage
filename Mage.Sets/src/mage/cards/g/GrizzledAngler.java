
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GrizzledAngler extends CardImpl {

    public GrizzledAngler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = GrislyAnglerfish.class;

        // {T}: Put the top two cards of your library into your graveyard. Then if there is a colorless creature card in your graveyard, transform Grizzled Angler.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GrizzledAnglerEffect(), new TapSourceCost()));
    }

    private GrizzledAngler(final GrizzledAngler card) {
        super(card);
    }

    @Override
    public GrizzledAngler copy() {
        return new GrizzledAngler(this);
    }
}

class GrizzledAnglerEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a colorless creature card in your graveyard");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public GrizzledAnglerEffect() {
        super(Outcome.Benefit);
        staticText = "Mill two cards. Then if there is a colorless creature card in your graveyard, transform {this}";
    }

    public GrizzledAnglerEffect(final GrizzledAnglerEffect effect) {
        super(effect);
    }

    @Override
    public GrizzledAnglerEffect copy() {
        return new GrizzledAnglerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.millCards(2, source, game);
            if (controller.getGraveyard().count(filter, source.getControllerId(), source, game) >= 1) {
                return new TransformSourceEffect().apply(game, source);
            }
        }
        return false;
    }
}
