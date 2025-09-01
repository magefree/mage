package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StragoAndRelm extends CardImpl {

    public StragoAndRelm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Sketch and Lore -- {2}{R}, {T}: Target opponent exiles cards from the top of their library until they exile an instant, sorcery, or creature card. You may cast that card without paying its mana cost. If you cast a creature spell this way, it gains haste and "At the beginning of the end step, sacrifice this creature." Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new StragoAndRelmEffect(), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability.withFlavorWord("Sketch and Lore"));
    }

    private StragoAndRelm(final StragoAndRelm card) {
        super(card);
    }

    @Override
    public StragoAndRelm copy() {
        return new StragoAndRelm(this);
    }
}

class StragoAndRelmEffect extends OneShotEffect {

    private enum StragoAndRelmTracker implements CardUtil.SpellCastTracker {
        instance;

        @Override
        public boolean checkCard(Card card, Game game) {
            return true;
        }

        @Override
        public void addCard(Card card, Ability source, Game game) {
            if (!card.isCreature(game)) {
                return;
            }
            for (ContinuousEffect effect : Arrays.asList(
                    new GainAbilityTargetEffect(HasteAbility.getInstance()),
                    new GainAbilityTargetEffect(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect()))
            )) {
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.getState().addEffect(effect, card.getSpellAbility());
                game.addEffect(effect, source);
            }
        }
    }

    StragoAndRelmEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent exiles cards from the top of their library until they exile an " +
                "instant, sorcery, or creature card. You may cast that card without paying its mana cost. " +
                "If you cast a creature spell this way, it gains haste and \"At the beginning of the end step, sacrifice this creature.\"";
    }

    private StragoAndRelmEffect(final StragoAndRelmEffect effect) {
        super(effect);
    }

    @Override
    public StragoAndRelmEffect copy() {
        return new StragoAndRelmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent == null) {
            return false;
        }
        Card card = getCard(opponent, game, source);
        if (card == null) {
            return false;
        }
        Optional.ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .ifPresent(player -> CardUtil.castSpellWithAttributesForFree(
                        player, source, game, new CardsImpl(card),
                        StaticFilters.FILTER_CARD, StragoAndRelmTracker.instance
                ));
        return true;
    }

    private static Card getCard(Player player, Game game, Ability source) {
        for (Card card : player.getLibrary().getCards(game)) {
            player.moveCards(card, Zone.EXILED, source, game);
            if (card.isInstantOrSorcery(game) || card.isCreature(game)) {
                return card;
            }
        }
        return null;
    }
}
