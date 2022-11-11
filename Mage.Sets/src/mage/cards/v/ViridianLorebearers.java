package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class ViridianLorebearers extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifacts your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public ViridianLorebearers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.ELF, SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {3}{G}, {tap}: Target creature gets +X/+X until end of turn, where X is the number of artifacts your opponents control.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn), new ManaCostsImpl<>("{3}{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ViridianLorebearers(final ViridianLorebearers card) {
        super(card);
    }

    @Override
    public ViridianLorebearers copy() {
        return new ViridianLorebearers(this);
    }
}
