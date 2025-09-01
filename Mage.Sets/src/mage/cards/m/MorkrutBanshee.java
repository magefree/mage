package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class MorkrutBanshee extends CardImpl {

    public MorkrutBanshee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // <i>Morbid</i> &mdash; When Morkut Banshee enters the battlefield, if a creature died this turn, target creature gets -4/-4 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-4, -4))
                .withInterveningIf(MorbidCondition.instance);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.MORBID).addHint(MorbidHint.instance));
    }

    private MorkrutBanshee(final MorkrutBanshee card) {
        super(card);
    }

    @Override
    public MorkrutBanshee copy() {
        return new MorkrutBanshee(this);
    }
}
