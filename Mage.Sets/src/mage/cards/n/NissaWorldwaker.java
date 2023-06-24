
package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class NissaWorldwaker extends CardImpl {

    private static final FilterPermanent filterForest = new FilterPermanent(SubType.FOREST, "Forest");

    public NissaWorldwaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{3}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NISSA);

        this.setStartingLoyalty(3);

        // +1: Target land you control becomes a 4/4 Elemental creature with trample.  It's still a land.
        LoyaltyAbility ability = new LoyaltyAbility(new BecomesCreatureTargetEffect(new NissaWorldwakerToken(), false, true, Duration.Custom), 1);
        ability.addTarget(new TargetPermanent(new FilterControlledLandPermanent()));
        this.addAbility(ability);

        // +1: Untap up to four target Forests.
        ability = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability.addTarget(new TargetPermanent(0, 4, filterForest, false));
        this.addAbility(ability);

        // -7: Search your library for any number of basic land cards, put them onto the battlefield, then shuffle your library.  Those lands become 4/4 Elemental creatures with trample.  They're still lands.
        this.addAbility(new LoyaltyAbility(new NissaWorldwakerSearchEffect(), -7));
    }

    private NissaWorldwaker(final NissaWorldwaker card) {
        super(card);
    }

    @Override
    public NissaWorldwaker copy() {
        return new NissaWorldwaker(this);
    }
}

class NissaWorldwakerSearchEffect extends OneShotEffect {

    public NissaWorldwakerSearchEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Search your library for any number of basic land cards, put them onto the battlefield, then shuffle. Those lands become 4/4 Elemental creatures with trample. They're still lands";
    }

    public NissaWorldwakerSearchEffect(final NissaWorldwakerSearchEffect effect) {
        super(effect);
    }

    @Override
    public NissaWorldwakerSearchEffect copy() {
        return new NissaWorldwakerSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_BASIC_LAND);
        if (controller.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                for (UUID cardId : target.getTargets()) {
                    Card card = controller.getLibrary().getCard(cardId, game);
                    if (card != null) {
                        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                            Permanent land = game.getPermanent(card.getId());
                            if (land != null) {
                                ContinuousEffect effect = new BecomesCreatureTargetEffect(new NissaWorldwakerToken(), false, true, Duration.Custom);
                                effect.setTargetPointer(new FixedTarget(land, game));
                                game.addEffect(effect, source);

                            }
                        }
                    }
                }
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}

class NissaWorldwakerToken extends TokenImpl {

    public NissaWorldwakerToken() {
        super("", "4/4 Elemental creature with trample");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(TrampleAbility.getInstance());
    }
    public NissaWorldwakerToken(final NissaWorldwakerToken token) {
        super(token);
    }

    public NissaWorldwakerToken copy() {
        return new NissaWorldwakerToken(this);
    }
}
