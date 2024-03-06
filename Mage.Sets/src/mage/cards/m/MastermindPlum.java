package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.watchers.common.ManaPaidSourceWatcher;

/**
 * Mastermind Plum {2}{B}
 * Legendary Creature - Human Wizard 2/2
 * Whenever Mastermind Plum attacks, exile up to one target card from a graveyard. If an artifact card was exiled this way, create a Treasure token.
 * Whenever you cast a spell, if mana from a Treasure was spent to cast it, you draw a card and you lose 1 life.
 *
 * @author DominionSpy
 */
public final class MastermindPlum extends CardImpl {

    public MastermindPlum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Mastermind Plum attacks, exile up to one target card from a graveyard. If an artifact card was exiled this way, create a Treasure token.
        Ability ability = new AttacksTriggeredAbility(new MastermindPlumEffect());
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        this.addAbility(ability);

        // Whenever you cast a spell, if mana from a Treasure was spent to cast it, you draw a card and you lose 1 life.
        ability = new ConditionalInterveningIfTriggeredAbility(
                new SpellCastControllerTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_SPELL,
                        false, SetTargetPointer.SPELL),
                MastermindPlumCondition.instance,
                "Whenever you cast a spell, if mana from a Treasure was spent to cast it, " +
                        "you draw a card and you lose 1 life."
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1));
        this.addAbility(ability);
    }

    private MastermindPlum(final MastermindPlum card) {
        super(card);
    }

    @Override
    public MastermindPlum copy() {
        return new MastermindPlum(this);
    }
}

class MastermindPlumEffect extends OneShotEffect {

    MastermindPlumEffect() {
        super(Outcome.Exile);
        staticText = "exile up to one target card from a graveyard. If an artifact card was exiled this way, create a Treasure token.";
    }

    private MastermindPlumEffect(final MastermindPlumEffect effect) {
        super(effect);
    }

    @Override
    public MastermindPlumEffect copy() {
        return new MastermindPlumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        if (cards.isEmpty()) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        if (cards.count(StaticFilters.FILTER_CARD_ARTIFACT, game) > 0) {
            Token treasure = new TreasureToken();
            treasure.putOntoBattlefield(1, game, source);
        }
        return true;
    }
}

enum MastermindPlumCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) source.getEffects().get(0).getValue("spellCast");
        return spell != null && ManaPaidSourceWatcher.getTreasurePaid(spell.getSourceId(), game) > 0;
    }
}
