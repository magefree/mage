package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.assignment.common.SubTypeAssignment;
import mage.abilities.common.CantPayLifeOrSacrificeAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YasharnImplacableEarth extends CardImpl {

    public YasharnImplacableEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Yasharn, Implacable Earth enters the battlefield, search your library for a basic Forest card and a basic Plains card, reveal those cards, put them into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new YasharnImplacableEarthTarget(), true)
                        .setText("search your library for a basic Forest card and a basic Plains card, "
                                + "reveal those cards, put them into your hand, then shuffle")
        ));

        // Players can't pay life or sacrifice nonland permanents to cast spells or activate abilities.
        this.addAbility(new CantPayLifeOrSacrificeAbility(StaticFilters.FILTER_PERMANENTS_NON_LAND));
    }

    private YasharnImplacableEarth(final YasharnImplacableEarth card) {
        super(card);
    }

    @Override
    public YasharnImplacableEarth copy() {
        return new YasharnImplacableEarth(this);
    }
}

class YasharnImplacableEarthTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("a basic Forest card and a basic Plains card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(Predicates.or(
                SubType.FOREST.getPredicate(),
                SubType.PLAINS.getPredicate()
        ));
    }

    private static final SubTypeAssignment subTypeAssigner = new SubTypeAssignment(SubType.FOREST, SubType.PLAINS);

    YasharnImplacableEarthTarget() {
        super(0, 2, filter);
    }

    private YasharnImplacableEarthTarget(final YasharnImplacableEarthTarget target) {
        super(target);
    }

    @Override
    public YasharnImplacableEarthTarget copy() {
        return new YasharnImplacableEarthTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return subTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }
}
