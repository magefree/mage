package mage.cards.a;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author nigelzor
 */
public final class AcceleratedMutation extends CardImpl {

    public AcceleratedMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}{G}");

        // Target creature gets +X/+X until end of turn, where X is the greatest converted mana cost among permanents you control.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS,
                GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS,
                Duration.EndOfTurn
        ));
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS.getHint());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AcceleratedMutation(final AcceleratedMutation card) {
        super(card);
    }

    @Override
    public AcceleratedMutation copy() {
        return new AcceleratedMutation(this);
    }
}
