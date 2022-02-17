
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class TrialOfAmbition extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Cartouche");

    static {
        filter.add(SubType.CARTOUCHE.getPredicate());
    }

    public TrialOfAmbition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // When Trial of Ambition enters the battlefield, target opponent sacrifices a creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_A_CREATURE, 1, "target opponent"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // When a Cartouche enters the battlefield under your control, return Trial of Ambition to its owner's hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new ReturnToHandSourceEffect(), filter,
                "When a Cartouche enters the battlefield under your control, return {this} to its owner's hand"));
    }

    private TrialOfAmbition(final TrialOfAmbition card) {
        super(card);
    }

    @Override
    public TrialOfAmbition copy() {
        return new TrialOfAmbition(this);
    }
}
