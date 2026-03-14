package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ShrinesYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KyoshiIslandPlaza extends CardImpl {

    public KyoshiIslandPlaza(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // When Kyoshi Island Plaza enters, search your library for up to X basic land cards, where X is the number of Shrines you control. Put those cards onto the battlefield tapped, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KyoshiIslandPlazaEffect())
                .addHint(ShrinesYouControlCount.getHint()));

        // Whenever another Shrine you control enters, search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ), StaticFilters.FILTER_ANOTHER_CONTROLLED_SHRINE));
    }

    private KyoshiIslandPlaza(final KyoshiIslandPlaza card) {
        super(card);
    }

    @Override
    public KyoshiIslandPlaza copy() {
        return new KyoshiIslandPlaza(this);
    }
}

class KyoshiIslandPlazaEffect extends OneShotEffect {

    KyoshiIslandPlazaEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for up to X basic land cards, where X is the number of Shrines you control. " +
                "Put those cards onto the battlefield tapped, then shuffle";
    }

    private KyoshiIslandPlazaEffect(final KyoshiIslandPlazaEffect effect) {
        super(effect);
    }

    @Override
    public KyoshiIslandPlazaEffect copy() {
        return new KyoshiIslandPlazaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = ShrinesYouControlCount.WHERE_X.calculate(game, source, this);
        return new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(0, count, StaticFilters.FILTER_CARD_BASIC_LANDS), true
        ).apply(game, source);
    }
}
