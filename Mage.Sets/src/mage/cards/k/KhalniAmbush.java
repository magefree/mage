package mage.cards.k;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class KhalniAmbush extends ModalDoubleFacedCard {

    public KhalniAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{2}{G}",
                "Khalni Territory", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Khalni Ambush
        // Instant

        // Target creature you control fights target creature you don't control.
        this.getLeftHalfCard().getSpellAbility().addEffect(new FightTargetsEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));

        // 2.
        // Khalni Territory
        // Land

        // Khalni Territory enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());
    }

    private KhalniAmbush(final KhalniAmbush card) {
        super(card);
    }

    @Override
    public KhalniAmbush copy() {
        return new KhalniAmbush(this);
    }
}
