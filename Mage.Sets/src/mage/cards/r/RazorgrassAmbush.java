package mage.cards.r;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RazorgrassAmbush extends ModalDoubleFacedCard {

    public RazorgrassAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{1}{W}",
                "Razorgrass Field", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Razorgrass Ambush
        // Instant

        // Razorgrass Ambush deals 3 damage to target attacking or blocking creature.
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
        this.getLeftHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(3));

        // 2.
        // Razorgrass Field
        // Land

        // As Razorgrass Field enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {W}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());
    }

    private RazorgrassAmbush(final RazorgrassAmbush card) {
        super(card);
    }

    @Override
    public RazorgrassAmbush copy() {
        return new RazorgrassAmbush(this);
    }
}
