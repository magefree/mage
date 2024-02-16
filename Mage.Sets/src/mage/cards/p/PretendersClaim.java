package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.BecomesBlockedAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class PretendersClaim extends CardImpl {

    public PretendersClaim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever enchanted creature becomes blocked, tap all lands defending player controls.
        this.addAbility(new BecomesBlockedAttachedTriggeredAbility(
                new TapAllTargetPlayerControlsEffect(StaticFilters.FILTER_LANDS)
                        .setText("tap all lands defending player controls"),
                false, SetTargetPointer.PLAYER
        ));
    }

    private PretendersClaim(final PretendersClaim card) {
        super(card);
    }

    @Override
    public PretendersClaim copy() {
        return new PretendersClaim(this);
    }
}
