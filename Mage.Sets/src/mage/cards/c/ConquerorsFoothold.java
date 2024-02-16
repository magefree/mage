
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class ConquerorsFoothold extends CardImpl {

    public ConquerorsFoothold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.nightCard = true;

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}: Draw a card, then discard a card.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DrawDiscardControllerEffect(),
                new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {4}, {T}: Draw a card.
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                new ManaCostsImpl<>("{4}"));
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);

        // {6}, {T}: Return target card from your graveyard to your hand.
        SimpleActivatedAbility ability3 = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ReturnFromGraveyardToHandTargetEffect(),
                new ManaCostsImpl<>("{6}"));
        ability3.addCost(new TapSourceCost());
        ability3.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability3);
    }

    private ConquerorsFoothold(final ConquerorsFoothold card) {
        super(card);
    }

    @Override
    public ConquerorsFoothold copy() {
        return new ConquerorsFoothold(this);
    }
}
