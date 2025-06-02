package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author anonymous
 */
public final class CoalstokeGearhulk extends CardImpl {
    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card with mana value 4 or less from a graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
    }
    public CoalstokeGearhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}{B}{R}{R}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When this creature enters, put target creature card with mana value 4 or less from a graveyard onto the battlefield under your control with a finality counter on it. That creature gains menace, deathtouch, and haste. At the beginnning of your next end step, exile that creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance()))
                              .setTriggerPhrase("When this creature enters, ");
        ability.addEffect(new GainAbilityTargetEffect(new MenaceAbility())
                              .setText("That creature gains menace"));
        ability.addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
                              .setText("deathtouch")
                              .concatBy(","));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                              .setText("haste")
                              .concatBy(", and"));
        ability.addEffect(new CoalstokeGearhulkEffect());
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    private CoalstokeGearhulk(final CoalstokeGearhulk card) {
        super(card);
    }

    @Override
    public CoalstokeGearhulk copy() {
        return new CoalstokeGearhulk(this);
    }
}

class CoalstokeGearhulkEffect extends OneShotEffect {
    public CoalstokeGearhulkEffect() {
        super(Outcome.Benefit);
        this.staticText = "At the beginning of your next end step, exile that creature.";
    }

    public CoalstokeGearhulkEffect(final CoalstokeGearhulkEffect effect) {
        super(effect);
    }

    @Override
    public CoalstokeGearhulkEffect copy() {
        return new CoalstokeGearhulkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        ExileTargetEffect exileEffect = new ExileTargetEffect("At the beginning of your next end step, exile " + permanent.getLogName());
        exileEffect.setTargetPointer(new FixedTarget(permanent, game));
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect, TargetController.YOU);
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}
