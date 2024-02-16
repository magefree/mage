package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class HouseGuildmage extends CardImpl {

    public HouseGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{U}, {T}: Target creature doesn't untap during its controller's next untap step.
        Ability ability = new SimpleActivatedAbility(
                new DontUntapInControllersNextUntapStepTargetEffect(),
                new ManaCostsImpl<>("{1}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {2}{B}, {T}: Surveil 2.
        ability = new SimpleActivatedAbility(
                new SurveilEffect(2),
                new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private HouseGuildmage(final HouseGuildmage card) {
        super(card);
    }

    @Override
    public HouseGuildmage copy() {
        return new HouseGuildmage(this);
    }
}
