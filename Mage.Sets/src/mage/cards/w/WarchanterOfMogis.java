package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WarchanterOfMogis extends CardImpl {

    public WarchanterOfMogis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // <i>Inspired</i> &mdash; Whenever Warchanter of Mogis becomes untapped, target creature you control gains intimidate until end of turn.
        Effect effect = new GainAbilityTargetEffect(IntimidateAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("target creature you control gains intimidate until end of turn. <i>(A creature with intimidate can't be blocked except by artifact creatures and/or creatures that share a color with it.)</i>");
        Ability ability = new InspiredAbility(effect);
        ability.addTarget(new TargetControlledCreaturePermanent());        
        this.addAbility(ability);
    }

    private WarchanterOfMogis(final WarchanterOfMogis card) {
        super(card);
    }

    @Override
    public WarchanterOfMogis copy() {
        return new WarchanterOfMogis(this);
    }
}
