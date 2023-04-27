package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.hint.common.DomainHint;
import mage.constants.*;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author weirddan455
 */
public final class MeriasOutrider extends CardImpl {

    public MeriasOutrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Domain--When Meria's Outrider enters the battlefield, it deals damage to each opponent equal to the number of basic land types among lands you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DamagePlayersEffect(Outcome.Damage, DomainValue.REGULAR, TargetController.OPPONENT)
                        .setText("it deals damage to each opponent equal to the number of basic land types among lands you control")
        ).setAbilityWord(AbilityWord.DOMAIN).addHint(DomainHint.instance));
    }

    private MeriasOutrider(final MeriasOutrider card) {
        super(card);
    }

    @Override
    public MeriasOutrider copy() {
        return new MeriasOutrider(this);
    }
}
