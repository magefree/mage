package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.DomainHint;
import mage.constants.AbilityWord;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.abilities.Ability;
import mage.target.common.TargetCardWithDifferentNameInLibrary;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class FblthpKnowsTheWay extends CardImpl {

    public FblthpKnowsTheWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HOMUNCULUS);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Domain -- Fblthp's power is equal to the number of basic land types among lands you control.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SetBasePowerSourceEffect(DomainValue.REGULAR)
            .setText("{this}'s power is equal to the number of basic land types among lands you control")
        ).addHint(DomainHint.instance).setAbilityWord(AbilityWord.DOMAIN));

        // When Fblthp enters, search your library for up to X basic land cards with different names, reveal them, put them into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FblthpKnowsTheWayEffect()));
    }

    private FblthpKnowsTheWay(final FblthpKnowsTheWay card) {
        super(card);
    }

    @Override
    public FblthpKnowsTheWay copy() {
        return new FblthpKnowsTheWay(this);
    }
}

class FblthpKnowsTheWayEffect extends OneShotEffect {

    FblthpKnowsTheWayEffect() {
        super(Outcome.DrawCard);
        staticText = "search your library for up to X basic land cards with different names, "
            + "reveal them, put them into your hand, then shuffle";
    }

    private FblthpKnowsTheWayEffect(final FblthpKnowsTheWayEffect effect) {
        super(effect);
    }

    @Override
    public FblthpKnowsTheWayEffect copy() {
        return new FblthpKnowsTheWayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = CardUtil.getSourceCostsTag(game, source, "X", 0);
        return new SearchLibraryPutInHandEffect(
            new TargetCardWithDifferentNameInLibrary(0, xValue, StaticFilters.FILTER_CARD_BASIC_LANDS),
            true
        ).apply(game, source);
    }
}
