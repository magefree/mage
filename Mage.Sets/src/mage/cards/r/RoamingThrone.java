package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.AddChosenSubtypeEffect;
import mage.abilities.effects.common.enterAttribute.EnterAttributeAddChosenSubtypeEffect;
import mage.abilities.effects.common.replacement.AdditionalTriggerObjectReplacementEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RoamingThrone extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature you control of the chosen type");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(ChosenSubtypePredicate.TRUE);
    }

    public RoamingThrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // As Roaming Throne enters the battlefield, choose a creature type.
        Ability ability = new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit));

        // Roaming Throne is the chosen type in addition to its other types.
        ability.addEffect(new EnterAttributeAddChosenSubtypeEffect());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(new AddChosenSubtypeEffect()));

        // If a triggered ability of another creature you control of the chosen type triggers, it triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AdditionalTriggerObjectReplacementEffect(filter)));
    }

    private RoamingThrone(final RoamingThrone card) {
        super(card);
    }

    @Override
    public RoamingThrone copy() {
        return new RoamingThrone(this);
    }
}
