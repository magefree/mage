package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.ScryTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class GlorfindelDauntlessRescuer extends CardImpl {

    public GlorfindelDauntlessRescuer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you scry, choose one and Glorfindel, Dauntless Rescuer gets +1/+1 until end of turn.
        // * Glorfindel must be blocked this turn if able.
        Ability ability = new ScryTriggeredAbility(
            new BoostSourceEffect(1, 1, Duration.EndOfTurn).setText("")
        );
        ability.addEffect(new MustBeBlockedByAtLeastOneSourceEffect(Duration.EndOfTurn));

        // * Glorfindel can't be blocked by more than one creature each combat this turn.
        Mode mode = new Mode(
            new BoostSourceEffect(1, 1, Duration.EndOfTurn).setText("")
        );
        mode.addEffect(new CantBeBlockedByMoreThanOneSourceEffect(1, Duration.EndOfTurn));

        ability.addMode(mode);
        ability.getModes().setChooseText("choose one and {this} gets +1/+1 until end of turn.");

        this.addAbility(ability);
    }

    private GlorfindelDauntlessRescuer(final GlorfindelDauntlessRescuer card) {
        super(card);
    }

    @Override
    public GlorfindelDauntlessRescuer copy() {
        return new GlorfindelDauntlessRescuer(this);
    }
}
