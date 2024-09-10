package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StrengthOfTheHarvest extends ModalDoubleFacedCard {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.ENCHANTMENT.getPredicate()));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public StrengthOfTheHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "{2}{G/W}",
                "Haven of the Harvest", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Strength of the Harvest
        // Enchantment - Aura

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getLeftHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getLeftHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getLeftHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +1/+1 for each creature and/or enchantment you control.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(
                new BoostEnchantedEffect(xValue, xValue, Duration.WhileOnBattlefield)
                        .setText("enchanted creature gets +1/+1 for each creature and/or enchantment you control")
        ).addHint(new ValueHint("Creatures and/or enchantments you control", xValue)));

        // 2.
        // Haven of the Harvest
        // Land

        // Haven of the Harvest enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G} or {W}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());
        this.getRightHalfCard().addAbility(new WhiteManaAbility());
    }

    private StrengthOfTheHarvest(final StrengthOfTheHarvest card) {
        super(card);
    }

    @Override
    public StrengthOfTheHarvest copy() {
        return new StrengthOfTheHarvest(this);
    }
}
