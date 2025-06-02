package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YiazmatUltimateMark extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another creature or artifact");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public YiazmatUltimateMark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);
        this.nightCard = true;
        this.color.setBlack(true);

        // {1}{B}, Sacrifice another creature or artifact: Yiazmat gains indestructible until end of turn. Tap it.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addEffect(new TapSourceEffect().setText("tap it"));
        this.addAbility(ability);
    }

    private YiazmatUltimateMark(final YiazmatUltimateMark card) {
        super(card);
    }

    @Override
    public YiazmatUltimateMark copy() {
        return new YiazmatUltimateMark(this);
    }
}
