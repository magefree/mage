package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FieldsOfStrife extends CardImpl {

    public FieldsOfStrife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R} or {W}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());

        // {2}{R}{W}, {T}: Surveil 1.
        Ability ability = new SimpleActivatedAbility(new SurveilEffect(1), new ManaCostsImpl<>("{2}{R}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private FieldsOfStrife(final FieldsOfStrife card) {
        super(card);
    }

    @Override
    public FieldsOfStrife copy() {
        return new FieldsOfStrife(this);
    }
}
