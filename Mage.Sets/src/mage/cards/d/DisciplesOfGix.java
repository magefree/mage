package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DisciplesOfGix extends CardImpl {

    public DisciplesOfGix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Disciples of Gix enters the battlefield, search your library for up to three artifact cards, put them into your graveyard, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DisciplesOfGixEffect()));
    }

    private DisciplesOfGix(final DisciplesOfGix card) {
        super(card);
    }

    @Override
    public DisciplesOfGix copy() {
        return new DisciplesOfGix(this);
    }
}


class DisciplesOfGixEffect extends OneShotEffect {

    public DisciplesOfGixEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for up to three artifact cards, put them into your graveyard, then shuffle";
    }

    public DisciplesOfGixEffect(final DisciplesOfGixEffect effect) {
        super(effect);
    }

    @Override
    public DisciplesOfGixEffect copy() {
        return new DisciplesOfGixEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(
                0, 3, StaticFilters.FILTER_CARD_ARTIFACT
        );
        controller.searchLibrary(target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        cards.retainZone(Zone.LIBRARY, game);
        controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        controller.shuffleLibrary(source, game);
        return true;
    }
}
