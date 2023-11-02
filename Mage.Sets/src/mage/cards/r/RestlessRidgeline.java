package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RestlessRidgeline extends CardImpl {

    static final FilterAttackingCreature filter = new FilterAttackingCreature("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public RestlessRidgeline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Restless Ridgeline enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());

        // {2}{R}{G}: Restless Ridgeline becomes a 3/4 red and green Dinosaur creature until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(3, 4, "3/4 red and green Dinosaur creature")
                        .withColor("RG").withSubType(SubType.DINOSAUR),
                CardType.LAND, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{2}{R}{G}")));

        // Whenever Restless Ridgeline attacks, another target attacking creature gets +2/+0 until end of turn. Untap that creature.
        Ability ability = new AttacksTriggeredAbility(
                new BoostTargetEffect(2, 0)
        );
        ability.addEffect(new UntapTargetEffect()
                .setText("untap that creature"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private RestlessRidgeline(final RestlessRidgeline card) {
        super(card);
    }

    @Override
    public RestlessRidgeline copy() {
        return new RestlessRidgeline(this);
    }
}
