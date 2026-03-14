package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.token.BearsCompanionBearToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheEarthKing extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public TheEarthKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When The Earth King enters, create a 4/4 green Bear creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BearsCompanionBearToken())));

        // Whenever one or more creatures you control with power 4 or greater attack, search your library for up to that many basic land cards, put them onto the battlefield tapped, then shuffle.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new TheEarthKingEffect(), 1, filter)
                .setTriggerPhrase("Whenever one or more creatures you control with power 4 or greater attack, "));
    }

    private TheEarthKing(final TheEarthKing card) {
        super(card);
    }

    @Override
    public TheEarthKing copy() {
        return new TheEarthKing(this);
    }
}

class TheEarthKingEffect extends OneShotEffect {

    TheEarthKingEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for up to that many basic land cards, " +
                "put them onto the battlefield tapped, then shuffle";
    }

    private TheEarthKingEffect(final TheEarthKingEffect effect) {
        super(effect);
    }

    @Override
    public TheEarthKingEffect copy() {
        return new TheEarthKingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = (Integer) getValue(AttacksWithCreaturesTriggeredAbility.VALUEKEY_NUMBER_ATTACKERS);
        return new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                0, count, StaticFilters.FILTER_CARD_BASIC_LANDS
        ), true).apply(game, source);
    }
}
