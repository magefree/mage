package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.GiantWizardToken;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class GiantsAmulet extends CardImpl {

    public GiantsAmulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Giant's Amulet enters the battlefield, you may pay {3}{U}. If you do, create a 4/4 blue Giant Wizard creature token, then attach Giant's Amulet to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new CreateTokenAttachSourceEffect(new GiantWizardToken()), new ManaCostsImpl<>("{3}{U}")
        )));

        // Equipped creature gets +0/+1 and has "This creature has hexproof as long as it's untapped."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(0, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new SimpleStaticAbility(new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                HexproofAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ), SourceTappedCondition.UNTAPPED,
                        "{this} has hexproof as long as it's untapped"
                )), AttachmentType.EQUIPMENT
        ).setText("and has \"This creature has hexproof as long as it's untapped.\""));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private GiantsAmulet(final GiantsAmulet card) {
        super(card);
    }

    @Override
    public GiantsAmulet copy() {
        return new GiantsAmulet(this);
    }
}
