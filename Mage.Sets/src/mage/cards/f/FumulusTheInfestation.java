package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.XiraBlackInsectToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FumulusTheInfestation extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("an Insect, Leech, Slug, or Worm you control");

    static {
        filter.add(Predicates.or(
                SubType.INSECT.getPredicate(),
                SubType.LEECH.getPredicate(),
                SubType.SLUG.getPredicate(),
                SubType.WORM.getPredicate()
        ));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public FumulusTheInfestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a player sacrifices a nontoken creature, create a 1/1 black Insect creature token with flying.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new CreateTokenEffect(new XiraBlackInsectToken()),
                StaticFilters.FILTER_CREATURE_NON_TOKEN, TargetController.ANY
        ));

        // Whenever an Insect, Leech, Slug, or Worm you control attacks, defending player loses 1 life and you gain 1 life.
        Ability ability = new AttacksAllTriggeredAbility(
                new LoseLifeTargetEffect(1), false,
                filter, SetTargetPointer.PLAYER, false
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private FumulusTheInfestation(final FumulusTheInfestation card) {
        super(card);
    }

    @Override
    public FumulusTheInfestation copy() {
        return new FumulusTheInfestation(this);
    }
}
