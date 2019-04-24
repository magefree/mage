package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChainwhipCyclops extends CardImpl {

    public ChainwhipCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {3}{R}: Target creature can't block this turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBlockTargetEffect(Duration.EndOfTurn), new ManaCostsImpl("{3}{R}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ChainwhipCyclops(final ChainwhipCyclops card) {
        super(card);
    }

    @Override
    public ChainwhipCyclops copy() {
        return new ChainwhipCyclops(this);
    }
}
