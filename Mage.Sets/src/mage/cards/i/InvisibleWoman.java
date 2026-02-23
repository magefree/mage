package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.common.CastNoncreatureSpellThisTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.WallColorlessReachToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvisibleWoman extends CardImpl {

    public InvisibleWoman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, if you've cast a noncreature spell this turn, create a 0/3 colorless Wall creature token with defender and reach.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new CreateTokenEffect(new WallColorlessReachToken()))
                .withInterveningIf(CastNoncreatureSpellThisTurnCondition.instance)
                .addHint(CastNoncreatureSpellThisTurnCondition.getHint()));

        // Whenever you attack, you may pay {R}{G}{W}{U}. When you do, target creature gets +1/+0 until end of turn for each creature you control and can't be blocked this turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new BoostTargetEffect(CreaturesYouControlCount.SINGULAR, StaticValue.get(0)), false
        );
        ability.addEffect(new CantBeBlockedTargetEffect().setText("and can't be blocked this turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new DoWhenCostPaid(
                ability, new ManaCostsImpl<>("{R}{G}{W}{U}"), "Pay {R}{G}{W}{U}?"
        ), 1));
    }

    private InvisibleWoman(final InvisibleWoman card) {
        super(card);
    }

    @Override
    public InvisibleWoman copy() {
        return new InvisibleWoman(this);
    }
}
