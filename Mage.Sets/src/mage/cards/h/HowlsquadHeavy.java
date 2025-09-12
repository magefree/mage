package mage.cards.h;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HowlsquadHeavy extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GOBLIN);
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Goblins you control", xValue);

    public HowlsquadHeavy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Other Goblins you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ).setText("other Goblins you control have haste")));

        // At the beginning of combat on your turn, create a 1/1 red Goblin creature token. That token attacks this combat if able.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new HowlsquadHeavyEffect()));

        // Max Speed -- {T}: Add {R} for each Goblin you control.
        this.addAbility(new MaxSpeedAbility(new DynamicManaAbility(Mana.RedMana(1), xValue)).addHint(hint));
    }

    private HowlsquadHeavy(final HowlsquadHeavy card) {
        super(card);
    }

    @Override
    public HowlsquadHeavy copy() {
        return new HowlsquadHeavy(this);
    }
}

class HowlsquadHeavyEffect extends OneShotEffect {

    HowlsquadHeavyEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 red Goblin creature token. That token attacks this combat if able";
    }

    private HowlsquadHeavyEffect(final HowlsquadHeavyEffect effect) {
        super(effect);
    }

    @Override
    public HowlsquadHeavyEffect copy() {
        return new HowlsquadHeavyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new GoblinToken();
        token.putOntoBattlefield(1, game, source);
        game.addEffect(new AttacksIfAbleTargetEffect(Duration.EndOfCombat)
                .setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}
