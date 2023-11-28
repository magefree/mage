package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class AuraShards extends CardImpl {

    public AuraShards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{W}");

        // Whenever a creature enters the battlefield under your control, 
        // you may destroy target artifact or enchantment.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
                new DestroyTargetEffect(), StaticFilters.FILTER_PERMANENT_CREATURE, true
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private AuraShards(final AuraShards card) {
        super(card);
    }

    @Override
    public AuraShards copy() {
        return new AuraShards(this);
    }
}
