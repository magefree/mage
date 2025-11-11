package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlABasicLandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SpiritWorldToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RealmOfKoh extends CardImpl {

    public RealmOfKoh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a basic land.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(YouControlABasicLandCondition.instance)
                .addHint(YouControlABasicLandCondition.getHint()));

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {3}{B}, {T}: Create a 1/1 colorless Spirit creature token with "This token can't block or be blocked by non-Spirit creatures."
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new SpiritWorldToken()), new ManaCostsImpl<>("{3}{B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private RealmOfKoh(final RealmOfKoh card) {
        super(card);
    }

    @Override
    public RealmOfKoh copy() {
        return new RealmOfKoh(this);
    }
}
