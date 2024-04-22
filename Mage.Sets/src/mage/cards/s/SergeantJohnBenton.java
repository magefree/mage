package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SergeantJohnBenton extends CardImpl {

    public SergeantJohnBenton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Share Intelligence -- Whenever Sergeant John Benton deals combat damage to a player, you and that player each draw that many cards.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedDamageValue.MANY)
                        .setText("you"),
                false,
                true
        );
        ability.addEffect(new DrawCardTargetEffect(SavedDamageValue.MANY)
                .setText("and that player each draw that many cards"));
        this.addAbility(ability.withFlavorWord("Share Intelligence"));
    }

    private SergeantJohnBenton(final SergeantJohnBenton card) {
        super(card);
    }

    @Override
    public SergeantJohnBenton copy() {
        return new SergeantJohnBenton(this);
    }
}
