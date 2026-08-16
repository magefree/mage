package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.Dwarf22Token;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnduringStoryCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.common.EnduringStoryHint;
import mage.abilities.keyword.StoriedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class FiliThePathfinder extends CardImpl {

   private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DWARF, "nontoken Dwarf you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public FiliThePathfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Storied
        this.addAbility(new StoriedAbility());

        // As long as you have an enduring story, creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield),
            EnduringStoryCondition.instance,
            "as long as you have an enduring story, creatures you control get +1/+1"
        )).addHint(EnduringStoryHint.instance));

        // Whenever Fili or another nontoken Dwarf you control enters, create a 2/2 red Dwarf creature token.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
            new CreateTokenEffect(new Dwarf22Token()), filter, false, false
        ));
    }

    private FiliThePathfinder(final FiliThePathfinder card) {
        super(card);
    }

    @Override
    public FiliThePathfinder copy() {
        return new FiliThePathfinder(this);
    }
}
