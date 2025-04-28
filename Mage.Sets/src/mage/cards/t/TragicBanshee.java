package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TragicBanshee extends CardImpl {

    public TragicBanshee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Morbid -- When this creature enters, target creature an opponent controls gets -1/-1 until end of turn. If a creature died this turn, that creature gets -13/-13 instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostTargetEffect(-13, -13)),
                new AddContinuousEffectToGame(new BoostTargetEffect(-1, -1)),
                MorbidCondition.instance, "target creature an opponent controls gets -1/-1 until end of turn. " +
                "If a creature died this turn, that creature gets -13/-13 until end of turn instead"
        ));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.addHint(MorbidHint.instance).setAbilityWord(AbilityWord.MORBID));
    }

    private TragicBanshee(final TragicBanshee card) {
        super(card);
    }

    @Override
    public TragicBanshee copy() {
        return new TragicBanshee(this);
    }
}
