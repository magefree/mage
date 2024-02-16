
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardForEachColorAmongControlledPermanentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SoulOfRavnica extends CardImpl {

    public SoulOfRavnica(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {5}{U}{U}: Draw a card for each color among permanents you control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardForEachColorAmongControlledPermanentsEffect(), new ManaCostsImpl<>("{5}{U}{U}")));

        // {5}{U}{U}, Exile Soul of Ravnica from your graveyard: Draw a card for each color among permanents you control.
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new DrawCardForEachColorAmongControlledPermanentsEffect(), new ManaCostsImpl<>("{5}{U}{U}"));
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private SoulOfRavnica(final SoulOfRavnica card) {
        super(card);
    }

    @Override
    public SoulOfRavnica copy() {
        return new SoulOfRavnica(this);
    }
}

