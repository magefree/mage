package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NezumiFreewheeler extends TransformingDoubleFacedCard {

    private static final FilterCard filter
            = new FilterPermanentCard("permanent card with mana value 2 or less from a graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public NezumiFreewheeler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.RAT, SubType.SAMURAI}, "{3}{B}",
                "Hideous Fleshwheeler",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.RAT}, "WB"
        );

        // Nezumi Freewheeler
        this.getLeftHalfCard().setPT(3, 3);

        // Menace
        this.getLeftHalfCard().addAbility(new MenaceAbility(false));

        // When Nezumi Freewheeler enters the battlefield, each player mills three cards.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsEachPlayerEffect(3, TargetController.EACH_PLAYER)));

        // {5}{W/P}: Transform Nezumi Freewheeler. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{5}{W/P}")));

        // Hideous Fleshwheeler
        this.getRightHalfCard().setPT(4, 5);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility());

        // When this creature transforms into Hideous Fleshwheeler, Hideous Fleshwheeler, put target permanent card with mana value 2 or less from a graveyard onto the battlefield under your control.
        Ability ability = new TransformIntoSourceTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.getRightHalfCard().addAbility(ability);
    }

    private NezumiFreewheeler(final NezumiFreewheeler card) {
        super(card);
    }

    @Override
    public NezumiFreewheeler copy() {
        return new NezumiFreewheeler(this);
    }
}
