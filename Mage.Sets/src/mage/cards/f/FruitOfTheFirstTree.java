package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttachedPermanentToughnessCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class FruitOfTheFirstTree extends CardImpl {

    public FruitOfTheFirstTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When enchanted creature dies, you gain X life and draw X cards, where X is its toughness.
        ability = new DiesAttachedTriggeredAbility(new GainLifeEffect(AttachedPermanentToughnessCount.instance)
                .setText("you gain X life"), "enchanted creature");
        ability.addEffect(new DrawCardSourceControllerEffect(AttachedPermanentToughnessCount.instance)
                .setText("and draw X cards, where X is its toughness"));
        this.addAbility(ability);
    }

    private FruitOfTheFirstTree(final FruitOfTheFirstTree card) {
        super(card);
    }

    @Override
    public FruitOfTheFirstTree copy() {
        return new FruitOfTheFirstTree(this);
    }
}
