package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoneDragon extends CardImpl {

    private static final FilterCard filter = new FilterCard("other cards");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BoneDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}{B}{B}, Exile seven other cards from your graveyard: Return Bone Dragon from your graveyard to the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(true),
                new ManaCostsImpl<>("{3}{B}{B}")
        );
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(7, filter)));
        this.addAbility(ability);
    }

    private BoneDragon(final BoneDragon card) {
        super(card);
    }

    @Override
    public BoneDragon copy() {
        return new BoneDragon(this);
    }
}
