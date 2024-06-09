package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EldraziRavager extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELDRAZI, "Eldrazi");

    public EldraziRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{C}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Annihilator 1
        this.addAbility(new AnnihilatorAbility(1));

        // Sacrifice two Eldrazi: Return Eldrazi Ravager from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(),
                new SacrificeTargetCost(2, filter)
        ));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private EldraziRavager(final EldraziRavager card) {
        super(card);
    }

    @Override
    public EldraziRavager copy() {
        return new EldraziRavager(this);
    }
}
