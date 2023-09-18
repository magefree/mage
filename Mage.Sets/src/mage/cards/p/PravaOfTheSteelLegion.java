package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PravaOfTheSteelLegion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public PravaOfTheSteelLegion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // As long as it's your turn, creature tokens you control get +1/+4.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(new BoostControlledEffect(
                1, 4, Duration.WhileOnBattlefield, filter
        ), MyTurnCondition.instance, "as long as it's your turn, creature tokens you control get +1/+4")));

        // {3}{W}: Create a 1/1 white Soldier creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new SoldierToken()), new ManaCostsImpl<>("{3}{W}")
        ));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private PravaOfTheSteelLegion(final PravaOfTheSteelLegion card) {
        super(card);
    }

    @Override
    public PravaOfTheSteelLegion copy() {
        return new PravaOfTheSteelLegion(this);
    }
}
