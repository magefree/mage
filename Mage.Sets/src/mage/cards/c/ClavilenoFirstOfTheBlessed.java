package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

public class ClavilenoFirstOfTheBlessed extends CardImpl {

    private static final FilterPermanent filter
            = new FilterAttackingCreature("attacking Vampire that isn't a Demon");

    static {
        filter.add(Predicates.and(
           SubType.VAMPIRE.getPredicate(),
           Predicates.not(SubType.DEMON.getPredicate())
        ));
    }

    public ClavilenoFirstOfTheBlessed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE, SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you attack, target attacking Vampire that isn't a Demon becomes a Demon in addition to its other types. It gains “When this creature dies, draw a card and create a tapped 4/3 white and black Vampire Demon creature token with flying.”
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new ClavilenoFirstOfTheBlessedEffect(), 1);
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

class ClavilenoFirstOfTheBlessedEffect extends OneShotEffect {

    private static final ContinuousEffect effect1 = new AddCardSubTypeTargetEffect(SubType.DEMON, Duration.WhileOnBattlefield);
    private static final ContinuousEffect effect2 = new GainAbilityTargetEffect(new ClavilenoFirstOfTheBlessedAbility(), Duration.WhileOnBattlefield);

    ClavilenoFirstOfTheBlessedEffect() {
        super(Outcome.AddAbility);
        staticText = "target attacking Vampire that isn't a Demon becomes a Demon in addition to its other types. It gains \"When this creature dies, draw a card and create a tapped 4/3 white and black Vampire Demon creature token with flying.\"";
    }

    private ClavilenoFirstOfTheBlessedEffect(final ClavilenoFirstOfTheBlessedEffect effect) {
        super(effect);
    }

    @Override
    public ClavilenoFirstOfTheBlessedEffect copy() {
        return new ClavilenoFirstOfTheBlessedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(effect1.setTargetPointer(getTargetPointer()), source);
        game.addEffect(effect2.setTargetPointer(getTargetPointer()), source);
        return true;
    }
}

class ClavilenoFirstOfTheBlessedAbility extends DiesSourceTriggeredAbility {

    public ClavilenoFirstOfTheBlessedAbility() {
        super(new DrawCardSourceControllerEffect(1), false);
        addEffect(new CreateTokenEffect(new VampireDemonToken(), 1, true));
    }
}

class VampireDemonToken extends TokenImpl {

    public VampireDemonToken() {
        super("Vampire Demon", "4/3 white and black Vampire Demon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE, SubType.DEMON);
        power = new MageInt(4);
        toughness = new MageInt(3);
        addAbility(FlyingAbility.getInstance());
    }

    protected VampireDemonToken(final VampireDemonToken token) {
        super(token);
    }

    public VampireDemonToken copy() {
        return new VampireDemonToken(this);
    }
}
