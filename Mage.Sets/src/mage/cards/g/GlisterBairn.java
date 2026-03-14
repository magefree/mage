package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlisterBairn extends CardImpl {

    public GlisterBairn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/U}{G/U}{G/U}");

        this.subtype.add(SubType.OUPHE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Vivid -- At the beginning of combat on your turn, another target creature you control gets +X/+X until end of turn, where X is the number of colors among permanents you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostTargetEffect(
                ColorsAmongControlledPermanentsCount.ALL_PERMANENTS,
                ColorsAmongControlledPermanentsCount.ALL_PERMANENTS
        ).setText("another target creature you control gets +X/+X until end of turn, " +
                "where X is the number of colors among permanents you control"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.VIVID).addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint()));
    }

    private GlisterBairn(final GlisterBairn card) {
        super(card);
    }

    @Override
    public GlisterBairn copy() {
        return new GlisterBairn(this);
    }
}
