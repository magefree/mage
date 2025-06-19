package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VonaButcherOfMagan extends CardImpl {

    public VonaButcherOfMagan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {T}, Pay 7 life: Destroy target nonland permanent. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DestroyTargetEffect(), new TapSourceCost(), MyTurnCondition.instance
        );
        ability.addCost(new PayLifeCost(7));
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private VonaButcherOfMagan(final VonaButcherOfMagan card) {
        super(card);
    }

    @Override
    public VonaButcherOfMagan copy() {
        return new VonaButcherOfMagan(this);
    }
}
