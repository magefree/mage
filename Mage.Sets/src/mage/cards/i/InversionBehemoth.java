package mage.cards.i;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessTargetEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author grimreap124
 */
public final class InversionBehemoth extends CardImpl {

    public InversionBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{C}{C}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(9);

        // At the beginning of combat on your turn, switch the power and toughness of each of any number of target creatures until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new SwitchPowerToughnessTargetEffect(Duration.EndOfTurn).setText("switch the power and toughness of each of any number of target creatures until end of turn."), TargetController.YOU, false);
        ability.addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
        this.addAbility(ability);
    }

    private InversionBehemoth(final InversionBehemoth card) {
        super(card);
    }

    @Override
    public InversionBehemoth copy() {
        return new InversionBehemoth(this);
    }
}
