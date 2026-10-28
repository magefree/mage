package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class ScottyChiefEngineer extends CardImpl {

    private static final FilterControlledArtifactPermanent filter =
        new FilterControlledArtifactPermanent("untapped artifact you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public ScottyChiefEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Tap an untapped artifact you control: Target creature can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(
            new CantBeBlockedTargetEffect(),
            new TapTargetCost(filter)
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ScottyChiefEngineer(final ScottyChiefEngineer card) {
        super(card);
    }

    @Override
    public ScottyChiefEngineer copy() {
        return new ScottyChiefEngineer(this);
    }
}
