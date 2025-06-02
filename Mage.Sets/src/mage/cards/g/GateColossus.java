package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.keyword.AffinityAbility;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GateColossus extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.GATE);

    public GateColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Affinity for Gates
        this.addAbility(new AffinityAbility(AffinityType.GATES));

        // Gate Colossus can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // Whenever a Gate you control enters, you may put Gate Colossus from your graveyard on top of your library.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.GRAVEYARD,
                new PutOnLibrarySourceEffect(
                        true, "put this card from your graveyard on top of your library"
                ), filter, true
        ));
    }

    private GateColossus(final GateColossus card) {
        super(card);
    }

    @Override
    public GateColossus copy() {
        return new GateColossus(this);
    }
}
