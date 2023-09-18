package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlasmaJockey extends CardImpl {

    public PlasmaJockey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Plasma Jockey attacks, target creature an opponent controls can't block this turn.
        Ability ability = new AttacksTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Blitz {2}{R}
        this.addAbility(new BlitzAbility(this, "{2}{R}"));
    }

    private PlasmaJockey(final PlasmaJockey card) {
        super(card);
    }

    @Override
    public PlasmaJockey copy() {
        return new PlasmaJockey(this);
    }
}
