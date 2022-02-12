package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class InfernoTitan extends CardImpl {

    public InfernoTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // {R}: Inferno Titan gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.R)));

        // Whenever Inferno Titan enters the battlefield or attacks, it deals 3 damage divided as you choose among one, two, or three targets.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DamageMultiEffect(3, "it"));
        ability.addTarget(new TargetAnyTargetAmount(3));
        this.addAbility(ability);
    }

    private InfernoTitan(final InfernoTitan card) {
        super(card);
    }

    @Override
    public InfernoTitan copy() {
        return new InfernoTitan(this);
    }
}
