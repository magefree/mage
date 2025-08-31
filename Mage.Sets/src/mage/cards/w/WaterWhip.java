package mage.cards.w;

import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaterWhip extends CardImpl {

    public WaterWhip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{U}");

        this.subtype.add(SubType.LESSON);

        // As an additional cost to cast this spell, waterbend {5}.
        this.getSpellAbility().addCost(new WaterbendCost(5));

        // Return up to two target creatures to their owners' hands. Draw two cards.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private WaterWhip(final WaterWhip card) {
        super(card);
    }

    @Override
    public WaterWhip copy() {
        return new WaterWhip(this);
    }
}
