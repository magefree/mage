package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DreadbringerLampads extends CardImpl {

    public DreadbringerLampads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.NYMPH);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Constellation - Whenever Dreadbringer Lampads or another enchantment enters the battlefield under your control, target creature gains intimidate until end of turn.
        Effect effect = new GainAbilityTargetEffect(IntimidateAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("target creature gains intimidate until end of turn. <i>(It can't be blocked except by artifact creatures and/or creatures that share a color with it.)</i>");
        Ability ability = new ConstellationAbility(effect);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DreadbringerLampads(final DreadbringerLampads card) {
        super(card);
    }

    @Override
    public DreadbringerLampads copy() {
        return new DreadbringerLampads(this);
    }
}
