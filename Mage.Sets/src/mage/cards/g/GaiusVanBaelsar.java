package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GaiusVanBaelsar extends CardImpl {

    public GaiusVanBaelsar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Gaius van Baelsar enters, choose one --
        // * Each player sacrifices a creature token of their choice.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SacrificeAllEffect(StaticFilters.FILTER_CREATURE_TOKEN));

        // * Each player sacrifices a nontoken creature of their choice.
        ability.addMode(new Mode(new SacrificeAllEffect(StaticFilters.FILTER_CREATURE_NON_TOKEN)));

        // * Each player sacrifices an enchantment of their choice.
        ability.addMode(new Mode(new SacrificeAllEffect(StaticFilters.FILTER_PERMANENT_ENCHANTMENT)));
        this.addAbility(ability);
    }

    private GaiusVanBaelsar(final GaiusVanBaelsar card) {
        super(card);
    }

    @Override
    public GaiusVanBaelsar copy() {
        return new GaiusVanBaelsar(this);
    }
}
