package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnchantedCreatureColorCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ScourgeOfTheNobilis extends CardImpl {

    public ScourgeOfTheNobilis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R/W}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // As long as enchanted creature is red, it gets +1/+1 and has "{RW}: This creature gets +1/+0 until end of turn."
        SimpleStaticAbility redAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostEnchantedEffect(1, 1), new EnchantedCreatureColorCondition(ObjectColor.RED), "As long as enchanted creature is red, it gets +1/+1"));
        redAbility.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(
                new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R/W}")), AttachmentType.AURA),
                new EnchantedCreatureColorCondition(ObjectColor.RED), "and has \"{R/W}: This creature gets +1/+0 until end of turn.\""));
        this.addAbility(redAbility);

        // As long as enchanted creature is white, it gets +1/+1 and has lifelink.
        SimpleStaticAbility whiteAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostEnchantedEffect(1, 1), new EnchantedCreatureColorCondition(ObjectColor.WHITE), "As long as enchanted creature is white, it gets +1/+1"));
        whiteAbility.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.AURA), new EnchantedCreatureColorCondition(ObjectColor.WHITE), "and has lifelink"));
        this.addAbility(whiteAbility);
    }

    private ScourgeOfTheNobilis(final ScourgeOfTheNobilis card) {
        super(card);
    }

    @Override
    public ScourgeOfTheNobilis copy() {
        return new ScourgeOfTheNobilis(this);
    }
}
