package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.CyclingAbility;
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
public final class QuakefootCyclops extends CardImpl {

    public QuakefootCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.CYCLOPS);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Quakefoot Cyclops enters the battlefield, up to two target creatures can't block this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);

        // Cycling {1}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{1}{R}")));

        // When you cycle Quakefoot Cyclops, target creature can't block this turn.
        ability = new CycleTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private QuakefootCyclops(final QuakefootCyclops card) {
        super(card);
    }

    @Override
    public QuakefootCyclops copy() {
        return new QuakefootCyclops(this);
    }
}
