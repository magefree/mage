package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StumpStomp extends ModalDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public StumpStomp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{1}{R/G}",
                "Burnwillow Clearing", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Stump Stomp
        // Sorcery

        // Target creature you control deals damage equal to its power to target creature or planeswalker you don't control.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter));

        // 2.
        // Burnwillow Clearing
        // Land

        // Burnwillow Clearing enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R} or {G}.
        this.getRightHalfCard().addAbility(new RedManaAbility());
        this.getRightHalfCard().addAbility(new GreenManaAbility());
    }

    private StumpStomp(final StumpStomp card) {
        super(card);
    }

    @Override
    public StumpStomp copy() {
        return new StumpStomp(this);
    }
}
