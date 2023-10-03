package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author North
 */
public final class MesaEnchantress extends CardImpl {

    public MesaEnchantress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_SPELL_AN_ENCHANTMENT, true));
    }

    private MesaEnchantress(final MesaEnchantress card) {
        super(card);
    }

    @Override
    public MesaEnchantress copy() {
        return new MesaEnchantress(this);
    }
}
