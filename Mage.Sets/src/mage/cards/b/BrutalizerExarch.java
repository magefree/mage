package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author North
 */
public final class BrutalizerExarch extends CardImpl {

    public BrutalizerExarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Brutalizer Exarch enters the battlefield, choose one
        // - Search your library for a creature card, reveal it, then shuffle your library and put that card on top of it;
        Ability ability = new EntersBattlefieldTriggeredAbility(new SearchLibraryPutOnLibraryEffect(
                new TargetCardInLibrary(new FilterCreatureCard("a creature card")), true
        ), false);

        // or put target noncreature permanent on the bottom of its owner's library.
        ability.addMode(new Mode(new PutOnLibraryTargetEffect(false))
                .addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_NON_CREATURE)));
        this.addAbility(ability);
    }

    private BrutalizerExarch(final BrutalizerExarch card) {
        super(card);
    }

    @Override
    public BrutalizerExarch copy() {
        return new BrutalizerExarch(this);
    }
}
