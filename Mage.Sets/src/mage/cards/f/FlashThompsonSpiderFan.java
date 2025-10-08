package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlashThompsonSpiderFan extends CardImpl {

    public FlashThompsonSpiderFan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Flash Thompson enters, choose one or both--
        // * Heckle -- Tap target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(2);
        ability.withFirstModeFlavorWord("Heckle");

        // * Hero Worship -- Untap target creature.
        ability.addMode(new Mode(new UntapTargetEffect())
                .addTarget(new TargetCreaturePermanent())
                .withFlavorWord("Hero Worship"));
        this.addAbility(ability);
    }

    private FlashThompsonSpiderFan(final FlashThompsonSpiderFan card) {
        super(card);
    }

    @Override
    public FlashThompsonSpiderFan copy() {
        return new FlashThompsonSpiderFan(this);
    }
}
