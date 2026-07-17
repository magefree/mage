package mage.cards.v;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.game.permanent.token.Elemental33BlueRedToken;

/**
 *
 * @author muz
 */
public final class VisionarysDance extends CardImpl {

    public VisionarysDance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}{R}");

        // Create two 3/3 blue and red Elemental creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Elemental33BlueRedToken(), 2));

        // {2}, Discard this card: Look at the top two cards of your library. Put one of them into your hand and the other into your graveyard.
        Ability discardAbility = new SimpleActivatedAbility(
            Zone.HAND,
            new LookLibraryAndPickControllerEffect(2, 1, PutCards.HAND, PutCards.GRAVEYARD),
            new ManaCostsImpl<>("{2}")
        );
        discardAbility.addCost(new DiscardSourceCost());
        this.addAbility(discardAbility);
    }

    private VisionarysDance(final VisionarysDance card) {
        super(card);
    }

    @Override
    public VisionarysDance copy() {
        return new VisionarysDance(this);
    }
}
