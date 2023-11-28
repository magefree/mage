package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.CreatureExploresTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MerfolkCaveDiver extends CardImpl {

    public MerfolkCaveDiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever a creature you control explores, Merfolk Cave-Diver gets +1/+0 until end of turn and can't be blocked this turn.
        Ability ability = new CreatureExploresTriggeredAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn));
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn).setText("and can't be blocked this turn"));
        this.addAbility(ability);
    }

    private MerfolkCaveDiver(final MerfolkCaveDiver card) {
        super(card);
    }

    @Override
    public MerfolkCaveDiver copy() {
        return new MerfolkCaveDiver(this);
    }
}
