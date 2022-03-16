package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedByTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class SpinEngine extends CardImpl {

    public SpinEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {R}: Target creature can't block Spin Engine this turn
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedByTargetSourceEffect(Duration.EndOfTurn),
                new ColoredManaCost(ColoredManaSymbol.R)
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SpinEngine(final SpinEngine card) {
        super(card);
    }

    @Override
    public SpinEngine copy() {
        return new SpinEngine(this);
    }
}
