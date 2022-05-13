package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaestrosInitiate extends CardImpl {

    public MaestrosInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {4}{U/R}, Exile Maestros Initiate from your graveyard: Draw two cards, then discard a card.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new DrawDiscardControllerEffect(2, 1),
                new ManaCostsImpl<>("{4}{U/R}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private MaestrosInitiate(final MaestrosInitiate card) {
        super(card);
    }

    @Override
    public MaestrosInitiate copy() {
        return new MaestrosInitiate(this);
    }
}
