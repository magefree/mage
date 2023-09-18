package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MaelstromArchangel extends CardImpl {

    private static final FilterCard filter = new FilterCard("a spell");

    public MaelstromArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Maelstrom Archangel deals combat damage to a player, you may cast a nonland card from your hand without paying its mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CastFromHandForFreeEffect(filter), false));
    }

    private MaelstromArchangel(final MaelstromArchangel card) {
        super(card);
    }

    @Override
    public MaelstromArchangel copy() {
        return new MaelstromArchangel(this);
    }
}
