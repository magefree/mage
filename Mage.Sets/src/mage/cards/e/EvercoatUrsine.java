package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.HideawayAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Grath
 */
public final class EvercoatUrsine extends CardImpl {

    public EvercoatUrsine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Hideaway 3
        this.addAbility(new HideawayAbility(this, 3));

        // Hideaway 3
        this.addAbility(new HideawayAbility(this, 3));

        // Whenever Evercoat Ursine deals combat damage to a player, if there are cards exiled with it, you may play
        // one of them without paying its mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new HideawayPlayEffect(true)));
    }

    private EvercoatUrsine(final EvercoatUrsine card) {
        super(card);
    }

    @Override
    public EvercoatUrsine copy() {
        return new EvercoatUrsine(this);
    }
}
