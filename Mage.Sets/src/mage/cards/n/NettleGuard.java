package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.ValiantTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NettleGuard extends CardImpl {

    public NettleGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Valiant -- Whenever Nettle Guard becomes the target of a spell or ability you control for the first time each turn, it gets +0/+2 until end of turn.
        this.addAbility(new ValiantTriggeredAbility(new BoostSourceEffect(
                0, 2, Duration.EndOfTurn, "it"
        )));

        // {1}, Sacrifice Nettle Guard: Destroy target artifact or enchantment.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private NettleGuard(final NettleGuard card) {
        super(card);
    }

    @Override
    public NettleGuard copy() {
        return new NettleGuard(this);
    }
}
