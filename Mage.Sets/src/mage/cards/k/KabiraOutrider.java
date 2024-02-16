package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.PartyCountHint;
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
public final class KabiraOutrider extends CardImpl {

    public KabiraOutrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Kabria Outrider enters the battlefield, target creature gets +1/+1 until end of turn for each creature in your party.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(
                PartyCount.instance, PartyCount.instance, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.addHint(PartyCountHint.instance));
    }

    private KabiraOutrider(final KabiraOutrider card) {
        super(card);
    }

    @Override
    public KabiraOutrider copy() {
        return new KabiraOutrider(this);
    }
}
