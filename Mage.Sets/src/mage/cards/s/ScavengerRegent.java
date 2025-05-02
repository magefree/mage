package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.OmenCard;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author Jmlundeen
 */
public final class ScavengerRegent extends OmenCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each non-Dragon creature");
    private static final DynamicValue xValue = new SignInversionDynamicValue(GetXValue.instance);

    static {
        filter.add(Predicates.not(SubType.DRAGON.getPredicate()));
    }

    public ScavengerRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{3}{B}", "Exude Toxin", "{X}{B}{B}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward--Discard a card.
        this.addAbility(new WardAbility(new DiscardCardCost()));

        // Exude Toxin
        // Each non-Dragon creature gets -X/-X until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new BoostAllEffect(xValue, xValue, Duration.EndOfTurn, filter, false));
        this.finalizeOmen();
    }

    private ScavengerRegent(final ScavengerRegent card) {
        super(card);
    }

    @Override
    public ScavengerRegent copy() {
        return new ScavengerRegent(this);
    }
}
