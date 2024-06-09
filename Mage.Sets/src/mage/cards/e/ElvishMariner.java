package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.ScryTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetScryAmount;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElvishMariner extends CardImpl {

    public ElvishMariner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Elvish Mariner attacks, scry 1.
        this.addAbility(new AttacksTriggeredAbility(new ScryEffect(1, false)));

        // Whenever you scry, tap up to X target nonland permanents, where X is the number of cards looked at while scrying this way.
        Ability ability = new ScryTriggeredAbility(new TapTargetEffect()
                .setText("tap up to X target nonland permanents, where X is " +
                        "the number of cards looked at while scrying this way"));
        ability.addTarget(new TargetNonlandPermanent(0, 1));
        ability.setTargetAdjuster(new TargetsCountAdjuster(GetScryAmount.instance));
        this.addAbility(ability);

    }

    private ElvishMariner(final ElvishMariner card) {
        super(card);
    }

    @Override
    public ElvishMariner copy() {
        return new ElvishMariner(this);
    }
}
