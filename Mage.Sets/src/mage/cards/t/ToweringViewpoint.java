package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToweringViewpoint extends CardImpl {

    public ToweringViewpoint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Leap of Faith -- {3}: Target creature gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(FlyingAbility.getInstance()), new GenericManaCost(3)
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Leap of Faith"));
    }

    private ToweringViewpoint(final ToweringViewpoint card) {
        super(card);
    }

    @Override
    public ToweringViewpoint copy() {
        return new ToweringViewpoint(this);
    }
}
