
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.DokaiWeaverofLifeToken;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;

import java.util.UUID;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.filter.StaticFilters;

/**
 * @author Loki
 */
public final class BudokaGardener extends CardImpl {

    public BudokaGardener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN, SubType.MONK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.flipCard = true;
        this.flipCardName = "Dokai, Weaver of Life";

        // {T}: You may put a land card from your hand onto the battlefield. If you control ten or more lands, flip Budoka Gardener.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A), new TapSourceCost());
        ability.addEffect(new BudokaGardenerEffect());
        this.addAbility(ability);
    }

    private BudokaGardener(final BudokaGardener card) {
        super(card);
    }

    @Override
    public BudokaGardener copy() {
        return new BudokaGardener(this);
    }

}

class BudokaGardenerEffect extends OneShotEffect {

    static final FilterControlledPermanent filterLands = new FilterControlledLandPermanent("lands you control");

    BudokaGardenerEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "If you control ten or more lands, flip {this}";
    }

    BudokaGardenerEffect(final BudokaGardenerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }
        if (game.getBattlefield().count(filterLands, source.getControllerId(), source, game) < 10) {
            return false;
        }

        return new FlipSourceEffect(new DokaiWeaverofLife()).apply(game, source);
    }

    @Override
    public BudokaGardenerEffect copy() {
        return new BudokaGardenerEffect(this);
    }

}

class DokaiWeaverofLife extends TokenImpl {

    DokaiWeaverofLife() {
        super("Dokai, Weaver of Life", "");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.HUMAN, SubType.MONK);
        power = new MageInt(3);
        toughness = new MageInt(3);

        // {4}{G}{G}, {T}: Create an X/X green Elemental creature token, where X is the number of lands you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new DokaiWeaverofLifeToken()), new ManaCostsImpl("{4}{G}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public DokaiWeaverofLife(final DokaiWeaverofLife token) {
        super(token);
    }

    public DokaiWeaverofLife copy() {
        return new DokaiWeaverofLife(this);
    }
}
