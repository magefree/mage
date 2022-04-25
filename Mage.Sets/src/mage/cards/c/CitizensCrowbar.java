package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeAttachmentCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.CitizenGreenWhiteToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CitizensCrowbar extends CardImpl {

    public CitizensCrowbar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Citizen's Crowbar enters the battlefield, create a 1/1 green and white Citizen creature token, then attach Citizen's Crowbar to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenAttachSourceEffect(new CitizenGreenWhiteToken())
        ));

        // Equipped creature gets +1/+1 and has "{W}, {T}, Sacrifice Citizen's Crowbar: Destroy target artifact or enchantment."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityWithAttachmentEffect(
                "and has \"{W}, {T}, Sacrifice {this}: Destroy target artifact or enchantment.\"",
                new DestroyTargetEffect(), new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT),
                new SacrificeAttachmentCost(), new ManaCostsImpl<>("{W}"), new TapSourceCost()
        ));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private CitizensCrowbar(final CitizensCrowbar card) {
        super(card);
    }

    @Override
    public CitizensCrowbar copy() {
        return new CitizensCrowbar(this);
    }
}
