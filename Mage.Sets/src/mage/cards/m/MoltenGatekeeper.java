package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoltenGatekeeper extends CardImpl {

    public MoltenGatekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another creature enters the battlefield under your control, Molten Gatekeeper deals 1 damage to each opponent.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), StaticFilters.FILTER_ANOTHER_CREATURE
        ));

        // Unearth {R}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{R}")));
    }

    private MoltenGatekeeper(final MoltenGatekeeper card) {
        super(card);
    }

    @Override
    public MoltenGatekeeper copy() {
        return new MoltenGatekeeper(this);
    }
}
