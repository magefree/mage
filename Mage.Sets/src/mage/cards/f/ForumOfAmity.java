package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForumOfAmity extends CardImpl {

    public ForumOfAmity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W} or {B}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // {2}{W}{B}, {T}: Surveil 1.
        Ability ability = new SimpleActivatedAbility(new SurveilEffect(1), new ManaCostsImpl<>("{2}{W}{B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ForumOfAmity(final ForumOfAmity card) {
        super(card);
    }

    @Override
    public ForumOfAmity copy() {
        return new ForumOfAmity(this);
    }
}
