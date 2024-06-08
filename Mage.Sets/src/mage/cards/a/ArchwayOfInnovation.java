package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.NextSpellCastHasAbilityEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ArchwayOfInnovation extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ISLAND);
    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public ArchwayOfInnovation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Archway of Innovation enters the battlefield tapped unless you control an Island.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {U}, {T}: The next spell you cast this turn has improvise.
        Ability ability = new SimpleActivatedAbility(
                new NextSpellCastHasAbilityEffect(new ImproviseAbility()),
                new ManaCostsImpl<>("{U}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ArchwayOfInnovation(final ArchwayOfInnovation card) {
        super(card);
    }

    @Override
    public ArchwayOfInnovation copy() {
        return new ArchwayOfInnovation(this);
    }
}
