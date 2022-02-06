package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SpiritGreenXToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KuraTheBoundlessSky extends CardImpl {

    public KuraTheBoundlessSky(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Kura, the Boundless Sky dies, choose one —
        // • Search your library for up to three land cards, reveal them, put them into your hand, then shuffle.
        Ability ability = new DiesSourceTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(0, 3, StaticFilters.FILTER_CARD_LANDS)
        ));

        // • Create an X/X green Spirit creature token, where X is the number of lands you control.
        ability.addMode(new Mode(new KuraTheBoundlessSkyEffect()));
        this.addAbility(ability.addHint(LandsYouControlHint.instance));
    }

    private KuraTheBoundlessSky(final KuraTheBoundlessSky card) {
        super(card);
    }

    @Override
    public KuraTheBoundlessSky copy() {
        return new KuraTheBoundlessSky(this);
    }
}

class KuraTheBoundlessSkyEffect extends OneShotEffect {

    KuraTheBoundlessSkyEffect() {
        super(Outcome.Benefit);
        staticText = "create an X/X green Spirit creature token, where X is the number of lands you control";
    }

    private KuraTheBoundlessSkyEffect(final KuraTheBoundlessSkyEffect effect) {
        super(effect);
    }

    @Override
    public KuraTheBoundlessSkyEffect copy() {
        return new KuraTheBoundlessSkyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = LandsYouControlCount.instance.calculate(game, source, this);
        return new SpiritGreenXToken(xValue).putOntoBattlefield(1, game, source);
    }
}
