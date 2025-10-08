package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.RemoveUpToAmountCountersEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author Grath
 */
public final class GlissaSunslayer extends CardImpl {

    public GlissaSunslayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELF);


        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        // First strike,
        this.addAbility(FirstStrikeAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Glissa Sunslayer deals combat damage to a player, choose one —
        // • You draw a card and you lose 1 life.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1).setText("you draw a card"), false, false);
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));

        // • Destroy target enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        ability.addMode(mode);

        // • Remove up to three counters from target permanent.
        mode = new Mode(new RemoveUpToAmountCountersEffect(3));
        mode.addTarget(new TargetPermanent());
        ability.addMode(mode);

        this.addAbility(ability);
    }

    private GlissaSunslayer(final GlissaSunslayer card) {
        super(card);
    }

    @Override
    public GlissaSunslayer copy() {
        return new GlissaSunslayer(this);
    }
}