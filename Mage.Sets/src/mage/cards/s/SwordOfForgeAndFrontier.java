package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 *
 * @author AhmadYProjects
 */
public final class SwordOfForgeAndFrontier extends CardImpl {

    public SwordOfForgeAndFrontier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from red and from green.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2,2));
        ability.addEffect(new GainAbilityAttachedEffect(
                ProtectionAbility.from(ObjectColor.GREEN,ObjectColor.RED), AttachmentType.EQUIPMENT).setText(
                "and has protection from red and from green"));
        this.addAbility(ability);
        // Whenever equipped creature deals combat damage to a player, exile the top two cards of your library. You may play those cards this turn. You may play an additional land this turn.
        Ability ability2 = new DealsDamageToAPlayerAttachedTriggeredAbility(new ExileTopXMayPlayUntilEndOfTurnEffect(2),"equipped creature", false);
        ability2.addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn));
        this.addAbility(ability2);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.Benefit,new GenericManaCost(2), false));
    }

    private SwordOfForgeAndFrontier(final SwordOfForgeAndFrontier card) {
        super(card);
    }

    @Override
    public SwordOfForgeAndFrontier copy() {
        return new SwordOfForgeAndFrontier(this);
    }
}
