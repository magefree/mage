package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlABasicLandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgnaQela extends CardImpl {

    public AgnaQela(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a basic land.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(YouControlABasicLandCondition.instance)
                .addHint(YouControlABasicLandCondition.getHint()));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {2}{U}, {T}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(1, 1), new ManaCostsImpl<>("{2}{U}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private AgnaQela(final AgnaQela card) {
        super(card);
    }

    @Override
    public AgnaQela copy() {
        return new AgnaQela(this);
    }
}
