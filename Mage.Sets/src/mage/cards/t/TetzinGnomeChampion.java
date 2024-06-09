package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.DoubleFacedCardPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class TetzinGnomeChampion extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("double-faced artifact");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.and(
                DoubleFacedCardPredicate.instance,
                CardType.ARTIFACT.getPredicate()
        )
        );
    }

    public TetzinGnomeChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.GNOME);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.t.TheGoldenGearColossus.class;
        this.color.setBlue(true);
        this.color.setRed(true);
        this.color.setWhite(true);

        // Whenever Tetzin or another double-faced artifact enters the battlefield under your control, mill three cards. You may put an artifact card from among them into your hand.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new MillThenPutInHandEffect(3, StaticFilters.FILTER_CARD_ARTIFACT_AN).withTextOptions("them"),
                filter, false, true
        ));

        // Craft with six artifacts 4 (4, Exile this artifact, Exile the six from among other permanents you control and/or cards from your graveyard: Return this card transformed under its owner's control. Craft only as a sorcery.)
        this.addAbility(new CraftAbility(
                "{4}", "six artifacts", "other artifacts you control and/or"
                + "artifact cards in your graveyard", 6, 6, CardType.ARTIFACT.getPredicate()
        ));
    }

    private TetzinGnomeChampion(final TetzinGnomeChampion card) {
        super(card);
    }

    @Override
    public TetzinGnomeChampion copy() {
        return new TetzinGnomeChampion(this);
    }

}
