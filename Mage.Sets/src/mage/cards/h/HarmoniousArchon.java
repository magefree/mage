package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarmoniousArchon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Archon creatures");

    static {
        filter.add(Predicates.not(SubType.ARCHON.getPredicate()));
    }

    public HarmoniousArchon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.ARCHON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Non-Archon creatures have base power and toughness 3/3.
        this.addAbility(new SimpleStaticAbility(new SetBasePowerToughnessAllEffect(
                3, 3, Duration.WhileOnBattlefield, filter, true
        )));

        // When Harmonious Archon enters the battlefield, create two 1/1 white Human creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanToken(), 2)));
    }

    private HarmoniousArchon(final HarmoniousArchon card) {
        super(card);
    }

    @Override
    public HarmoniousArchon copy() {
        return new HarmoniousArchon(this);
    }
}
