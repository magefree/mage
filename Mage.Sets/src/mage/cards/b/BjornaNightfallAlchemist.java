package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.FriendsForeverAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BjornaNightfallAlchemist extends CardImpl {

    public BjornaNightfallAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}, Sacrifice an artifact: Lucas, the Sharpshooter deals 1 damage to target creature. Goad that creature.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN)));
        ability.addEffect(new GoadTargetEffect().setText("Goad that creature"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Friends forever
        this.addAbility(FriendsForeverAbility.getInstance());
    }

    private BjornaNightfallAlchemist(final BjornaNightfallAlchemist card) {
        super(card);
    }

    @Override
    public BjornaNightfallAlchemist copy() {
        return new BjornaNightfallAlchemist(this);
    }
}
