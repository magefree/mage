package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawnbringerCleric extends CardImpl {

    public DawnbringerCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Dawnbringer Cleric enters the battlefield, choose one —
        // • Cure Wounds — You gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2));
        ability.getModes().getMode().withFlavorWord("Cure Wounds");

        // • Dispel Magic — Destroy target enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        ability.addMode(mode.withFlavorWord("Dispel Magic"));

        // • Gentle Repose — Exile target card from a graveyard.
        mode = new Mode(new ExileTargetEffect());
        mode.addTarget(new TargetCardInGraveyard());
        ability.addMode(mode.withFlavorWord("Gentle Repose"));
        this.addAbility(ability);
    }

    private DawnbringerCleric(final DawnbringerCleric card) {
        super(card);
    }

    @Override
    public DawnbringerCleric copy() {
        return new DawnbringerCleric(this);
    }
}
