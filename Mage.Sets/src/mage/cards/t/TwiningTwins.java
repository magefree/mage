package mage.cards.t;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TwiningTwins extends AdventureCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");
    static {
        filter.add(TokenPredicate.FALSE);
    }

    public TwiningTwins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FAERIE, SubType.WIZARD}, "{2}{U}{U}",
                "Swift Spiral",
                new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Twining Twins
        this.getLeftHalfCard().setPT(4, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.getLeftHalfCard().addAbility(VigilanceAbility.getInstance());

        // Ward {1}
        this.getLeftHalfCard().addAbility(new WardAbility(new GenericManaCost(1), false));

        // Swift Spiral
        // Exile target nontoken creature. Return it to the battlefield under its owner's control at the beginning of the next end step.
        this.getRightHalfCard().getSpellAbility().addEffect(new ExileReturnBattlefieldNextEndStepTargetEffect().withTextThatCard(false));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter));

        finalizeCard();
    }

    private TwiningTwins(final TwiningTwins card) {
        super(card);
    }

    @Override
    public TwiningTwins copy() {
        return new TwiningTwins(this);
    }
}
