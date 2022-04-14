package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HypnoticGrifter extends CardImpl {

    public HypnoticGrifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {3}: Hypnotic Grifter connives.
        this.addAbility(new SimpleActivatedAbility(new ConniveSourceEffect(), new GenericManaCost(3)));
    }

    private HypnoticGrifter(final HypnoticGrifter card) {
        super(card);
    }

    @Override
    public HypnoticGrifter copy() {
        return new HypnoticGrifter(this);
    }
}
