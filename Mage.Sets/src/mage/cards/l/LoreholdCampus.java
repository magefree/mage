package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoreholdCampus extends CardImpl {

    public LoreholdCampus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Lorehold Campus enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R} or {W}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());

        // {4}, {T}: Scry 1.
        Ability ability = new SimpleActivatedAbility(new ScryEffect(1, false), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private LoreholdCampus(final LoreholdCampus card) {
        super(card);
    }

    @Override
    public LoreholdCampus copy() {
        return new LoreholdCampus(this);
    }
}
