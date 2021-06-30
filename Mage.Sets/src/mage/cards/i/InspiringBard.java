package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InspiringBard extends CardImpl {

    public InspiringBard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Inspiring Bard enters the battlefield, choose one —
        // • Bardic Inspiration — Target creature gets +2/+2 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 2));
        ability.addTarget(new TargetCreaturePermanent());
        ability.getModes().getMode().withFlavorWord("Bardic Inspiration");

        // • Song of Rest — You gain 3 life.
        ability.addMode(new Mode(
                new GainLifeEffect(3)
        ).withFlavorWord("Song of Rest"));
        this.addAbility(ability);
    }

    private InspiringBard(final InspiringBard card) {
        super(card);
    }

    @Override
    public InspiringBard copy() {
        return new InspiringBard(this);
    }
}
