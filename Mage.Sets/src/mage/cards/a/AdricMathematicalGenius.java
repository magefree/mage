package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetStackAbilityEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterStackObject;
import mage.target.common.TargetActivatedOrTriggeredAbility;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AdricMathematicalGenius extends CardImpl {

    private static final FilterStackObject filter
            = new FilterStackObject("activated or triggered ability you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public AdricMathematicalGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{U}, {T}: Copy target activated or triggered ability you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new CopyTargetStackAbilityEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetActivatedOrTriggeredAbility(filter));
        this.addAbility(ability);

        // Ultimate Sacrifice -- {1}{U}, Sacrifice Adric: Counter target activated or triggered ability.
        ability = new SimpleActivatedAbility(new CounterTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetActivatedOrTriggeredAbility());
        this.addAbility(ability.withFlavorWord("Ultimate Sacrifice"));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private AdricMathematicalGenius(final AdricMathematicalGenius card) {
        super(card);
    }

    @Override
    public AdricMathematicalGenius copy() {
        return new AdricMathematicalGenius(this);
    }
}
