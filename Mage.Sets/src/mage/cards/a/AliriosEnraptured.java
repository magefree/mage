package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.ReflectionBlueToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AliriosEnraptured extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.REFLECTION);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public AliriosEnraptured(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Alirios, Enraptured enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Alirios doesn't untap during your untap step if you control a Reflection.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousRuleModifyingEffect(
                new DontUntapInControllersUntapStepSourceEffect(), condition
        ).setText("{this} doesn't untap during your untap step if you control a Reflection")));

        // When Alirios enters the battlefield, create a 3/2 blue Reflection creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ReflectionBlueToken())));
    }

    private AliriosEnraptured(final AliriosEnraptured card) {
        super(card);
    }

    @Override
    public AliriosEnraptured copy() {
        return new AliriosEnraptured(this);
    }
}
