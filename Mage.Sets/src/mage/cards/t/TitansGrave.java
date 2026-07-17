package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TitansGrave extends CardImpl {

    public TitansGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

        // {2}{B}{G}, {T}: Surveil 1.
        Ability ability = new SimpleActivatedAbility(new SurveilEffect(1), new ManaCostsImpl<>("{2}{B}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TitansGrave(final TitansGrave card) {
        super(card);
    }

    @Override
    public TitansGrave copy() {
        return new TitansGrave(this);
    }
}
