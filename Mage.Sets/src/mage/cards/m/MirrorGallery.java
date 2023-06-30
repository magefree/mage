package mage.cards.m;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.LegendRuleDoesntApplyEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MirrorGallery extends CardImpl {

    public MirrorGallery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // The "legend rule" doesn't apply.
        this.addAbility(new SimpleStaticAbility(new LegendRuleDoesntApplyEffect()));
    }

    private MirrorGallery(final MirrorGallery card) {
        super(card);
    }

    @Override
    public MirrorGallery copy() {
        return new MirrorGallery(this);
    }
}
