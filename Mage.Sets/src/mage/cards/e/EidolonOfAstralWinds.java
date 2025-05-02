package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EidolonOfAstralWinds extends CardImpl {

    public EidolonOfAstralWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Constellation -- Whenever Eidolon of Astral Winds or another enchantment you control enters, choose target creature you control. Until end of turn, that creature has base power and toughness 4/4 and gains flying.
        Ability ability = new ConstellationAbility(new SetBasePowerToughnessTargetEffect(4, 4, Duration.EndOfTurn)
                .setText("choose target creature you control. Until end of turn, that creature has base power and toughness 4/4"), false, true);
        ability.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance()).setText("and gains flying"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private EidolonOfAstralWinds(final EidolonOfAstralWinds card) {
        super(card);
    }

    @Override
    public EidolonOfAstralWinds copy() {
        return new EidolonOfAstralWinds(this);
    }
}
