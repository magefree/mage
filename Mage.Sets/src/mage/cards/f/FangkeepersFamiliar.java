package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FangkeepersFamiliar extends CardImpl {

    public FangkeepersFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");

        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this creature enters, choose one --
        // * You gain 3 life and surveil 3.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3));
        ability.addEffect(new SurveilEffect(3).concatBy("and"));

        // * Destroy target enchantment.
        ability.addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetEnchantmentPermanent()));

        // * Counter target creature spell.
        ability.addMode(new Mode(new CounterTargetEffect()).addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE)));
        this.addAbility(ability);
    }

    private FangkeepersFamiliar(final FangkeepersFamiliar card) {
        super(card);
    }

    @Override
    public FangkeepersFamiliar copy() {
        return new FangkeepersFamiliar(this);
    }
}
