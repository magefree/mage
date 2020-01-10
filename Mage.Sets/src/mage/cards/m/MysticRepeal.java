package mage.cards.m;

import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysticRepeal extends CardImpl {

    public MysticRepeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Put target enchantment on the bottom of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(false));
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
    }

    private MysticRepeal(final MysticRepeal card) {
        super(card);
    }

    @Override
    public MysticRepeal copy() {
        return new MysticRepeal(this);
    }
}
