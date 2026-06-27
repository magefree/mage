package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author muz
 */
public final class ThorGodOfThunder extends CardImpl {

    private static final FilterCard filter = new FilterCard("Equipment, instant, or sorcery card from your graveyard");

    static {
        filter.add(Predicates.or(
            SubType.EQUIPMENT.getPredicate(),
            CardType.INSTANT.getPredicate(),
            CardType.SORCERY.getPredicate()
        ));
    }

    public ThorGodOfThunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Thor enters, exile target Equipment, instant, or sorcery card from your graveyard.
        // Until the end of your next turn, you may play that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ThorGodOfThunderEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Whenever you cast a noncreature spell, Thor deals damage equal to that spell's mana value to any target.
        ability = new SpellCastControllerTriggeredAbility(new DamageTargetEffect(ThorGodOfThunderValue.instance)
            .setText("{this} deals damage equal to that spell's mana value to any target"),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ThorGodOfThunder(final ThorGodOfThunder card) {
        super(card);
    }

    @Override
    public ThorGodOfThunder copy() {
        return new ThorGodOfThunder(this);
    }
}

class ThorGodOfThunderEffect extends OneShotEffect {

    ThorGodOfThunderEffect() {
        super(Outcome.Benefit);
        staticText = "exile target Equipment, instant, or sorcery card from your graveyard. " +
                "Until the end of your next turn, you may play that card";
    }

    private ThorGodOfThunderEffect(final ThorGodOfThunderEffect effect) {
        super(effect);
    }

    @Override
    public ThorGodOfThunderEffect copy() {
        return new ThorGodOfThunderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (controller == null || card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        CardUtil.makeCardPlayable(game, source, card, false, Duration.UntilEndOfYourNextTurn, false);
        return true;
    }
}

enum ThorGodOfThunderValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
            .ofNullable(effect.getValue("spellCast"))
            .filter(Spell.class::isInstance)
            .map(Spell.class::cast)
            .map(Spell::getManaValue)
            .orElse(0);
    }

    @Override
    public ThorGodOfThunderValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "that spell's mana value";
    }

    @Override
    public String toString() {
        return "";
    }
}
