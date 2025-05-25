package mage.cards.b;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoonOfBoseiju extends CardImpl {

    public BoonOfBoseiju(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +X/+X until end of turn, where X is the greatest mana value among permanents you control. Untap that creature.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS,
                GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS,
                Duration.EndOfTurn
        ));
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS.getHint());
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BoonOfBoseiju(final BoonOfBoseiju card) {
        super(card);
    }

    @Override
    public BoonOfBoseiju copy() {
        return new BoonOfBoseiju(this);
    }
}