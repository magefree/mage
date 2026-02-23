package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.TransformTargetEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.DoubleFacedCardPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.GnomeToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class TetzinGnomeChampion extends TransformingDoubleFacedCard {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("double-faced artifact");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("other target double-faced artifact you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.and(
                DoubleFacedCardPredicate.instance,
                CardType.ARTIFACT.getPredicate())
        );
        filter2.add(AnotherPredicate.instance);
        filter2.add(Predicates.and(
                DoubleFacedCardPredicate.instance,
                CardType.ARTIFACT.getPredicate())
        );
    }

    public TetzinGnomeChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.GNOME}, "{U}{R}{W}",
                "The Golden-Gear Colossus",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.GNOME}, "URW"
        );

        // Tetzin, Gnome Champion
        this.getLeftHalfCard().setPT(2, 2);

        // Whenever Tetzin or another double-faced artifact you control enters, mill three cards. You may put an artifact card from among them into your hand.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new MillThenPutInHandEffect(3, StaticFilters.FILTER_CARD_ARTIFACT_AN).withTextOptions("them"),
                filter, false, true
        ));

        // Craft with six artifacts 4 (4, Exile this artifact, Exile the six from among other permanents you control and/or cards from your graveyard: Return this card transformed under its owner's control. Craft only as a sorcery.)
        this.getLeftHalfCard().addAbility(new CraftAbility(
                "{4}", "six artifacts", "other artifacts you control and/or"
                + "artifact cards in your graveyard", 6, 6, CardType.ARTIFACT.getPredicate()
        ));

        // The Golden-Gear Colossus
        this.getRightHalfCard().setPT(6, 6);

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever The Golden-Gear Colossus enters the battlefield or attacks, transform up to one other target double-faced artifact you control. Create two 1/1 colorless Gnome artifact creature tokens.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new TransformTargetEffect());
        TargetPermanent target = new TargetPermanent(0, 1, filter2);
        ability.addTarget(target);
        ability.addEffect(new CreateTokenEffect(new GnomeToken(), 2));
        this.getRightHalfCard().addAbility(ability);

    }

    private TetzinGnomeChampion(final TetzinGnomeChampion card) {
        super(card);
    }

    @Override
    public TetzinGnomeChampion copy() {
        return new TetzinGnomeChampion(this);
    }

}
