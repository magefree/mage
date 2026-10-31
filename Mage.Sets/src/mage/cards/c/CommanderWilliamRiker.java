package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author muz
 */
public final class CommanderWilliamRiker extends CardImpl {

    public CommanderWilliamRiker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {1}, {T}: You and target opponent each draw two cards.
        Ability ability = new SimpleActivatedAbility(
            new DrawCardSourceControllerEffect(2).setText("you"), new ManaCostsImpl<>("{1}")
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new DrawCardTargetEffect(2).setText("and target opponent each draw two cards"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private CommanderWilliamRiker(final CommanderWilliamRiker card) {
        super(card);
    }

    @Override
    public CommanderWilliamRiker copy() {
        return new CommanderWilliamRiker(this);
    }
}
