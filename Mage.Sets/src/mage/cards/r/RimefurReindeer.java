package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RimefurReindeer extends CardImpl {

    public RimefurReindeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ELK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever an enchantment enters the battlefield under your control, tap target creature an opponent controls.
        Ability ability = new ConstellationAbility(new TapTargetEffect(), false, false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.setAbilityWord(null));
    }

    private RimefurReindeer(final RimefurReindeer card) {
        super(card);
    }

    @Override
    public RimefurReindeer copy() {
        return new RimefurReindeer(this);
    }
}
