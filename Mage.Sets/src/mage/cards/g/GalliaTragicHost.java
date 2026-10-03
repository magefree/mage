package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class GalliaTragicHost extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("another creature card");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GalliaTragicHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SATYR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // {4}{B}, Exile another creature card from your graveyard: Return this card from your graveyard to the battlefield tapped with a +1/+1 counter on her.
        Ability ability = new SimpleActivatedAbility(
            Zone.GRAVEYARD,
            new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(
                CounterType.P1P1.createInstance(), true
            ).setText("return this card from your graveyard to the battlefield tapped with a +1/+1 counter on her"),
            new ManaCostsImpl<>("{4}{B}")
        );
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(filter)));
        this.addAbility(ability);
    }

    private GalliaTragicHost(final GalliaTragicHost card) {
        super(card);
    }

    @Override
    public GalliaTragicHost copy() {
        return new GalliaTragicHost(this);
    }
}
