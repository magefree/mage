package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StrixLookout extends CardImpl {

    public StrixLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {1}{U}, {T}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(new DrawDiscardControllerEffect(1, 1), new TapSourceCost());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private StrixLookout(final StrixLookout card) {
        super(card);
    }

    @Override
    public StrixLookout copy() {
        return new StrixLookout(this);
    }
}
