package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.RavenousAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Sporocyst extends CardImpl {

    public Sporocyst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{X}{G}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ravenous
        this.addAbility(new RavenousAbility());

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Spore Chimney -- When Sporocyst enters the battlefield, search your library for up to X basic land cards, put them onto the battlefield tapped, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SporocystEffect()).withFlavorWord("Spore Chimney"));
    }

    private Sporocyst(final Sporocyst card) {
        super(card);
    }

    @Override
    public Sporocyst copy() {
        return new Sporocyst(this);
    }
}

class SporocystEffect extends OneShotEffect {

    SporocystEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for up to X basic land cards, " +
                "put them onto the battlefield tapped, then shuffle";
    }

    private SporocystEffect(final SporocystEffect effect) {
        super(effect);
    }

    @Override
    public SporocystEffect copy() {
        return new SporocystEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = ManacostVariableValue.ETB.calculate(game, source, this);
        return new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                0, xValue, StaticFilters.FILTER_CARD_BASIC_LANDS
        ), true).apply(game, source);
    }
}
