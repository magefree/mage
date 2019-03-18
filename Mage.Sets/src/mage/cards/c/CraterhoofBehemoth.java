package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

import java.util.UUID;

/**
 * @author North
 */
public final class CraterhoofBehemoth extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public CraterhoofBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(HasteAbility.getInstance());

        // When Craterhoof Behemoth enters the battlefield, creatures you control gain trample and get +X/+X until end of turn, where X is the number of creatures you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, filter));
        ability.addEffect(new BoostControlledEffect(CreaturesYouControlCount.instance, CreaturesYouControlCount.instance, Duration.EndOfTurn, filter, false, true));
        ability.addHint(CreaturesYouControlHint.instance);
        this.addAbility(ability);
    }

    public CraterhoofBehemoth(final CraterhoofBehemoth card) {
        super(card);
    }

    @Override
    public CraterhoofBehemoth copy() {
        return new CraterhoofBehemoth(this);
    }
}
