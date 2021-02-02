package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Hollowsage extends CardImpl {

    public Hollowsage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Hollowsage becomes untapped, you may have target player discard a card.
        Ability ability = new InspiredAbility(new DiscardTargetEffect(1), true, false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private Hollowsage(final Hollowsage card) {
        super(card);
    }

    @Override
    public Hollowsage copy() {
        return new Hollowsage(this);
    }
}
