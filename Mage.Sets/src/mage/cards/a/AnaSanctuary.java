package mage.cards.a;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SanctuaryTriggeredAbility;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnaSanctuary extends CardImpl {

    public AnaSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of your upkeep, if you control a blue or black permanent, target creature gets +1/+1 until end of turn. If you control a blue permanent and a black permanent, that creature gets +5/+5 until end of turn instead.
        Ability ability = new SanctuaryTriggeredAbility(
                new AddContinuousEffectToGame(new BoostTargetEffect(1, 1)),
                new AddContinuousEffectToGame(new BoostTargetEffect(5, 5)),
                ObjectColor.BLACK, ObjectColor.BLUE, "target creature gets +1/+1 until end of turn. " +
                "If you control a blue permanent and a black permanent, that creature gets +5/+5 until end of turn instead."
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AnaSanctuary(final AnaSanctuary card) {
        super(card);
    }

    @Override
    public AnaSanctuary copy() {
        return new AnaSanctuary(this);
    }
}
