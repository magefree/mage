package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.hint.common.CaseSolvedHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.WasDealtDamageThisTurnPredicate;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

/**
 * Case of the Gorgon's Kiss {B}
 * Enchantment - Case
 * When this Case enters the battlefield, destroy up to one target creature that was dealt damage this turn.
 * To solve -- Three or more creature cards were put into graveyards from anywhere this turn.
 * Solved -- This Case is a 4/4 Gorgon creature with deathtouch and lifelink in addition to its other types.
 *
 * @author DominionSpy
 */
public final class CaseOfTheGorgonsKiss extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature that was dealt damage this turn");

    static {
        filter.add(WasDealtDamageThisTurnPredicate.instance);
    }

    public CaseOfTheGorgonsKiss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        this.subtype.add(SubType.CASE);

        // When this Case enters the battlefield, destroy up to one target creature that was dealt damage this turn.
        Ability initialAbility = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect())
                .setTriggerPhrase("When this Case enters the battlefield, ");
        initialAbility.addTarget(new TargetPermanent(0, 1, filter));
        // To solve -- Three or more creature cards were put into graveyards from anywhere this turn.
        Condition toSolveCondition = new CaseOfTheGorgonsKissCondition();
        // Solved -- This Case is a 4/4 Gorgon creature with deathtouch and lifelink in addition to its other types.
        Ability solvedAbility = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BecomesCreatureSourceEffect(new CaseOfTheGorgonsKissToken(),
                        CardType.ENCHANTMENT, Duration.WhileOnBattlefield),
                SolvedSourceCondition.SOLVED, "")
                .setText("This Case is a 4/4 Gorgon creature with deathtouch and lifelink in addition to its other types."));

        this.addAbility(new CaseAbility(initialAbility, toSolveCondition, solvedAbility)
                .addHint(new CaseOfTheGorgonsKissHint(toSolveCondition)),
                new CardsPutIntoGraveyardWatcher());
    }

    private CaseOfTheGorgonsKiss(final CaseOfTheGorgonsKiss card) {
        super(card);
    }

    @Override
    public CaseOfTheGorgonsKiss copy() {
        return new CaseOfTheGorgonsKiss(this);
    }
}

class CaseOfTheGorgonsKissCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        CardsPutIntoGraveyardWatcher watcher = game.getState().getWatcher(CardsPutIntoGraveyardWatcher.class);
        return watcher != null &&
                watcher.getCardsPutIntoGraveyardFromAnywhere(game)
                        .stream()
                        .filter(MageObject::isCreature)
                        .count() >= 3;
    }

    @Override
    public String toString() {
        return "Three or more creature cards were put into graveyards from anywhere this turn";
    }
}

class CaseOfTheGorgonsKissHint extends CaseSolvedHint {

    CaseOfTheGorgonsKissHint(Condition condition) {
        super(condition);
    }

    private CaseOfTheGorgonsKissHint(final CaseOfTheGorgonsKissHint hint) {
        super(hint);
    }

    @Override
    public CaseOfTheGorgonsKissHint copy() {
        return new CaseOfTheGorgonsKissHint(this);
    }

    @Override
    public String getConditionText(Game game, Ability ability) {
        int creatures = (int)game.getState()
                .getWatcher(CardsPutIntoGraveyardWatcher.class)
                .getCardsPutIntoGraveyardFromAnywhere(game)
                .stream()
                .filter(MageObject::isCreature)
                .count();
        return "Creatures put into graveyards: " + creatures + " (need 3).";
    }
}

class CaseOfTheGorgonsKissToken extends TokenImpl {

    CaseOfTheGorgonsKissToken() {
        super("", "4/4 Gorgon creature with deathtouch and lifelink");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add(SubType.GORGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(DeathtouchAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
    }

    private CaseOfTheGorgonsKissToken(final CaseOfTheGorgonsKissToken token) {
        super(token);
    }

    @Override
    public CaseOfTheGorgonsKissToken copy() {
        return new CaseOfTheGorgonsKissToken(this);
    }
}
