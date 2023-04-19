package mage.cards.m;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MercilessRepurposing extends CardImpl {

    public MercilessRepurposing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}{B}");

        // Exile target creature. Incubate 3.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new IncubateEffect(3));
    }

    private MercilessRepurposing(final MercilessRepurposing card) {
        super(card);
    }

    @Override
    public MercilessRepurposing copy() {
        return new MercilessRepurposing(this);
    }
}
