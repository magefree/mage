package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SlobadGoblinTinkerer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact");
    private static final FilterControlledPermanent filterControlled = new FilterControlledPermanent("an artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filterControlled.add(CardType.ARTIFACT.getPredicate());
    }

    public SlobadGoblinTinkerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Sacrifice an artifact: Target artifact is indestructible this turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledPermanent(filterControlled))
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SlobadGoblinTinkerer(final SlobadGoblinTinkerer card) {
        super(card);
    }

    @Override
    public SlobadGoblinTinkerer copy() {
        return new SlobadGoblinTinkerer(this);
    }
}
