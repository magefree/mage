package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.DarettiConstructToken;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Flamewright extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("creature with defender");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    public Flamewright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}, {tap}: Create a 1/1 colorless Construct artifact creature token with defender.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new DarettiConstructToken()), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {tap}, Sacrifice a creature with defender: Flamewright deals 1 damage to any target.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private Flamewright(final Flamewright card) {
        super(card);
    }

    @Override
    public Flamewright copy() {
        return new Flamewright(this);
    }
}
