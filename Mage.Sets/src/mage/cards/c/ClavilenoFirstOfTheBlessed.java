package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.VampireDemonToken;
import mage.target.TargetPermanent;

/**
 * @author DominionSpy
 */
public class ClavilenoFirstOfTheBlessed extends CardImpl {

    private static final FilterPermanent filter
            = new FilterAttackingCreature("attacking Vampire that isn't a Demon");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
        filter.add(Predicates.not(SubType.DEMON.getPredicate()));
    }

    public ClavilenoFirstOfTheBlessed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE, SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you attack, target attacking Vampire that isn't a Demon becomes a Demon in addition to its other types. It gains “When this creature dies, draw a card and create a tapped 4/3 white and black Vampire Demon creature token with flying.”
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new AddCardSubTypeTargetEffect(SubType.DEMON, Duration.Custom), 1);
        Ability gainedAbility = new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1), false);
        gainedAbility.addEffect(new CreateTokenEffect(new VampireDemonToken(), 1, true));
        ability.addEffect(new GainAbilityTargetEffect(gainedAbility, Duration.Custom));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ClavilenoFirstOfTheBlessed(final ClavilenoFirstOfTheBlessed card) {
        super(card);
    }

    @Override
    public ClavilenoFirstOfTheBlessed copy() {
        return new ClavilenoFirstOfTheBlessed(this);
    }
}
