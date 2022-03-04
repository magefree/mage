package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PersistentSpecimen extends CardImpl {

    public PersistentSpecimen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{B}: Return Persistent Specimen from your graveyard to the battlefield tapped.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(true, false),
                new ManaCostsImpl<>("{2}{B}")
        ));
    }

    private PersistentSpecimen(final PersistentSpecimen card) {
        super(card);
    }

    @Override
    public PersistentSpecimen copy() {
        return new PersistentSpecimen(this);
    }
}
