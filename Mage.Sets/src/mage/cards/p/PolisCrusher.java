package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterEnchantmentCard;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2, notgreat
 */
public final class PolisCrusher extends CardImpl {

    private static final FilterCard filterCard = new FilterEnchantmentCard("enchantments");
    private static final FilterPermanent filterPermanent = new FilterEnchantmentPermanent("enchantment that player controls");

    public PolisCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");
        this.subtype.add(SubType.CYCLOPS);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // protection from enchantments
        this.addAbility(new ProtectionAbility(filterCard));

        // {4}{R}{G}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{4}{R}{G}", 3));

        // Whenever Polis Crusher deals combat damage to a player, if Polis Crusher is monstrous, destroy target enchantment that player controls.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new DestroyTargetEffect(), false, true
        ).withInterveningIf(MonstrousCondition.instance);
        ability.addTarget(new TargetPermanent(filterPermanent));
        this.addAbility(ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster()));
    }

    private PolisCrusher(final PolisCrusher card) {
        super(card);
    }

    @Override
    public PolisCrusher copy() {
        return new PolisCrusher(this);
    }
}
