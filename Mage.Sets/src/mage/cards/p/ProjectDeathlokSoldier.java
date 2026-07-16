package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ProjectDeathlokSoldier extends CardImpl {

    public ProjectDeathlokSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {2}{B}: Return this card from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(
            Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{2}{B}")
        ));
    }

    private ProjectDeathlokSoldier(final ProjectDeathlokSoldier card) {
        super(card);
    }

    @Override
    public ProjectDeathlokSoldier copy() {
        return new ProjectDeathlokSoldier(this);
    }
}
