package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.AttachmentType;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZombieBerserkerToken;

/**
 *
 * @author weirddan455
 */
public final class DraugrsHelm extends CardImpl {

    public DraugrsHelm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Draugr's Helm enters the battlefield, you may pay {2}{B}.
        // If you do, create a 2/2 black Zombie Berserker creature token, then attach Draugr's Helm to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new CreateTokenAttachSourceEffect(new ZombieBerserkerToken()), new ManaCostsImpl<>("{2}{B}")
        )));

        // Equipped creature gets +2/+2 and has menace.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                new MenaceAbility(true),
                AttachmentType.EQUIPMENT).setText("and has menace. " +
                "<i>(It can't be blocked except by two or more creatures.)</i>"));
        this.addAbility(ability);

        // Equip {4}
        this.addAbility(new EquipAbility(4, false));
    }

    private DraugrsHelm(final DraugrsHelm card) {
        super(card);
    }

    @Override
    public DraugrsHelm copy() {
        return new DraugrsHelm(this);
    }
}
