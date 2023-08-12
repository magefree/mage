package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HedronDetonator extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledArtifactPermanent("artifacts");

    public HedronDetonator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever an artifact enters the battlefield under your control, Hedron Detonator deals 1 damage to target opponent.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new DamageTargetEffect(1), StaticFilters.FILTER_PERMANENT_ARTIFACT_AN
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {T}, Sacrifice two artifacts: Exile the top card of your library. You may play that card this turn.
        ability = new SimpleActivatedAbility(new ExileTopXMayPlayUntilEndOfTurnEffect(1), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, filter)));
        this.addAbility(ability);
    }

    private HedronDetonator(final HedronDetonator card) {
        super(card);
    }

    @Override
    public HedronDetonator copy() {
        return new HedronDetonator(this);
    }
}
