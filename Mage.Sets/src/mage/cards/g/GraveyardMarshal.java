package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class GraveyardMarshal extends CardImpl {

    private static final FilterCreatureCard filter
            = new FilterCreatureCard("a creature card from your graveyard");

    public GraveyardMarshal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {2}{B}, Exile a creature card from your graveyard: Create a tapped 2/2 black Zombie creature token.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(
                        new ZombieToken(),
                        1, true, false
                ),
                new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new ExileFromGraveCost(
                new TargetCardInYourGraveyard(filter)
        ));
        this.addAbility(ability);
    }

    private GraveyardMarshal(final GraveyardMarshal card) {
        super(card);
    }

    @Override
    public GraveyardMarshal copy() {
        return new GraveyardMarshal(this);
    }
}
