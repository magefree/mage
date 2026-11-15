package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class HexhavenInvigorator extends CardImpl {

    public HexhavenInvigorator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{G}{G}");

        this.subtype.add(SubType.CHIMERA);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever this creature is dealt damage, you may search your library for up to that many land cards, put them onto the battlefield tapped, then shuffle.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
            new HexhavenInvigoratorEffect(SavedDamageValue.MUCH), true
        ));
    }

    private HexhavenInvigorator(final HexhavenInvigorator card) {
        super(card);
    }

    @Override
    public HexhavenInvigorator copy() {
        return new HexhavenInvigorator(this);
    }
}

class HexhavenInvigoratorEffect extends OneShotEffect {

    private final DynamicValue amount;

    HexhavenInvigoratorEffect(DynamicValue amount) {
        super(Outcome.Benefit);
        staticText = "search your library for up to that many land cards, "
            + "put them onto the battlefield tapped, then shuffle";
        this.amount = amount;
    }

    private HexhavenInvigoratorEffect(final HexhavenInvigoratorEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public HexhavenInvigoratorEffect copy() {
        return new HexhavenInvigoratorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = amount.calculate(game, source, this);
        return new SearchLibraryPutInPlayEffect(
            new TargetCardInLibrary(0, count, StaticFilters.FILTER_CARD_LANDS), true
        ).apply(game, source);
    }
}
