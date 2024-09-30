package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MinesOfMoria extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public MinesOfMoria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // Mines of Moria enters the battlefield tapped unless you control a legendary creature.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {3}{R}, {T}, Exile three cards from your graveyard: Create two Treasure tokens.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new TreasureToken(), 2), new ManaCostsImpl<>("{3}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(3)));
        this.addAbility(ability);
    }

    private MinesOfMoria(final MinesOfMoria card) {
        super(card);
    }

    @Override
    public MinesOfMoria copy() {
        return new MinesOfMoria(this);
    }
}
