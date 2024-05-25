package mage.cards.b;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FirstTargetPointer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BridgeworksBattle extends ModalDoubleFacedCard {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public BridgeworksBattle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{2}{G}",
                "Tanglespan Bridgeworks", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Bridgeworks Battle
        // Sorcery

        // Target creature you control gets +2/+2 until end of turn. It fights up to one target creature you don't control.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new BoostTargetEffect(2, 2)
                        .setTargetPointer(new FirstTargetPointer())
        );
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new FightTargetsEffect()
                        .setText("It fights up to one target creature you don't control")
        );
        this.getLeftHalfCard().getSpellAbility().addTarget(
                new TargetControlledCreaturePermanent()
        );
        this.getLeftHalfCard().getSpellAbility().addTarget(
                new TargetPermanent(0, 1, filter, false)
        );

        // 2.
        // Tanglespan Bridgeworks
        // Land

        // As Tanglespan Bridgeworks enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {G}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());
    }

    private BridgeworksBattle(final BridgeworksBattle card) {
        super(card);
    }

    @Override
    public BridgeworksBattle copy() {
        return new BridgeworksBattle(this);
    }
}
