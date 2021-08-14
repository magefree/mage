package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScionOfStygia extends CardImpl {

    public ScionOfStygia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Cone of Cold — When Scion of Stygia enters the battlefield, choose target creature an opponent controls, then roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(
                20, "choose target creature an opponent controls, then roll a d20"
        );
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Cone of Cold"));

        // 1-9 | Tap that creature.
        effect.addTableEntry(1, 9, new TapTargetEffect("tap that creature"));

        // 10-20 | Tap that creature. It doesn't untap during its controller's next untap step.
        effect.addTableEntry(
                10, 20, new TapTargetEffect("tap that creature"),
                new DontUntapInControllersNextUntapStepTargetEffect("it")
        );
    }

    private ScionOfStygia(final ScionOfStygia card) {
        super(card);
    }

    @Override
    public ScionOfStygia copy() {
        return new ScionOfStygia(this);
    }
}
