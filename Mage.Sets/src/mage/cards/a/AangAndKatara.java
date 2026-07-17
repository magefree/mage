package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AangAndKatara extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("tapped artifacts and/or creatures you control");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);
    private static final Hint hint = new ValueHint("Tapped artifacts and creatures you control", xValue);

    public AangAndKatara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Aang and Katara enter or attack, create X 1/1 white Ally creature tokens, where X is the number of tapped artifacts and/or creatures you control.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CreateTokenEffect(new AllyToken(), xValue))
                .setTriggerPhrase("Whenever {this} enter or attack, ").addHint(hint));
    }

    private AangAndKatara(final AangAndKatara card) {
        super(card);
    }

    @Override
    public AangAndKatara copy() {
        return new AangAndKatara(this);
    }
}
