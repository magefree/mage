package mage.cards.t;

import mage.MageInt;
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
import mage.target.common.TargetCreaturePermanent;

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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{2}{U}{U}", "Swift Spiral", "{1}{W}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Ward {1}
        this.addAbility(new WardAbility(new GenericManaCost(1), false));

        // Swift Spiral
        // Exile target nontoken creature. Return it to the battlefield under its owner’s control at the beginning of the next end step.
        this.getSpellCard().getSpellAbility().addEffect(new ExileReturnBattlefieldNextEndStepTargetEffect().withTextThatCard(false));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        this.finalizeAdventure();
    }

    private TwiningTwins(final TwiningTwins card) {
        super(card);
    }

    @Override
    public TwiningTwins copy() {
        return new TwiningTwins(this);
    }
}
