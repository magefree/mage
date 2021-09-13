
package mage.cards.e;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author fireshoes
 */
public final class ElusiveTormentor extends CardImpl {

    public ElusiveTormentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.i.InsidiousMist.class;

        // {1}, Discard a card: Transform Elusive Tormentor.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(), new GenericManaCost(1));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private ElusiveTormentor(final ElusiveTormentor card) {
        super(card);
    }

    @Override
    public ElusiveTormentor copy() {
        return new ElusiveTormentor(this);
    }
}
