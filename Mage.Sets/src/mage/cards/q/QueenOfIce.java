package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QueenOfIce extends AdventureCard {

    public QueenOfIce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.NOBLE, SubType.WIZARD}, "{2}{U}",
                "Rage of Winter",
                new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Queen of Ice
        this.getLeftHalfCard().setPT(2, 3);

        // Whenever Queen of Ice deals combat damage to a creature, tap that creature. It doesn't untap during its controller's next untap step.
        Ability ability = new DealsDamageToACreatureTriggeredAbility(
                new TapTargetEffect("tap that creature"),
                true, false, true
        );
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect()
                .setText("It doesn't untap during its controller's next untap step")
        );
        this.getLeftHalfCard().addAbility(ability);

        // Rage of Winter
        // Tap target creature. It doesn't untap during its controller's next untap step.
        this.getRightHalfCard().getSpellAbility().addEffect(new TapTargetEffect());
        this.getRightHalfCard().getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect()
                .setText("It doesn't untap during its controller's next untap step"));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        finalizeCard();
    }

    private QueenOfIce(final QueenOfIce card) {
        super(card);
    }

    @Override
    public QueenOfIce copy() {
        return new QueenOfIce(this);
    }
}
// let it go
