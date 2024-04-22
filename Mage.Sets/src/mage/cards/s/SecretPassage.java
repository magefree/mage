package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SecretPassage extends CardImpl {

    public SecretPassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Secret Passage enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U} or {B}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());

        // {4}, {T}: Investigate.
        Ability ability = new SimpleActivatedAbility(new InvestigateEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SecretPassage(final SecretPassage card) {
        super(card);
    }

    @Override
    public SecretPassage copy() {
        return new SecretPassage(this);
    }
}
