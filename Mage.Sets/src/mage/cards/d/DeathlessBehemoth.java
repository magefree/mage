package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DeathlessBehemoth extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Eldrazi Scions");

    static {
        filter.add(SubType.ELDRAZI.getPredicate());
        filter.add(SubType.SCION.getPredicate());
    }

    public DeathlessBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Sacrifice two Eldrazi Scions: Return Deathless Behemoth from your graveyard to your hand. Activate this ability only any time you could cast a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(2, filter))
        ));
    }

    private DeathlessBehemoth(final DeathlessBehemoth card) {
        super(card);
    }

    @Override
    public DeathlessBehemoth copy() {
        return new DeathlessBehemoth(this);
    }
}
