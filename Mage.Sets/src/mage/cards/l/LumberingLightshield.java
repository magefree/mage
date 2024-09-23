package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetPerpetuallyEffect;
import mage.abilities.effects.common.cost.SpellCostIncreaseSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class LumberingLightshield extends CardImpl {

    public LumberingLightshield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Lumbering Lightshield enters, target opponent reveals a nonland card at random from their hand. It perpetually gains “This spell costs {1} more to cast.”
        Ability ability = new EntersBattlefieldTriggeredAbility(new LumberingLightshieldEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    private LumberingLightshield(final LumberingLightshield card) {
        super(card);
    }

    @Override
    public LumberingLightshield copy() {
        return new LumberingLightshield(this);
    }
}

class LumberingLightshieldEffect extends OneShotEffect {

    LumberingLightshieldEffect() {
        super(Outcome.AddAbility);
        this.staticText = "target opponent reveals a nonland card at random from their hand. It perpetually gains “This spell costs {1} more to cast.”";
    }

    private LumberingLightshieldEffect(final LumberingLightshieldEffect effect) {
        super(effect);
    }

    @Override
    public LumberingLightshieldEffect copy() {
        return new LumberingLightshieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent != null && !opponent.getHand().isEmpty()) {
            Cards revealed = new CardsImpl();

            Set<Card> cards = opponent.getHand()
                    .getCards(game)
                    .stream()
                    .filter(card -> StaticFilters.FILTER_CARD_NON_LAND.match(card, getId(), source, game))
                    .collect(Collectors.toSet());

            Card card = RandomUtil.randomFromCollection(cards);
            if (card != null) {
                revealed.add(card);
                opponent.revealCards("Lumbering Lightshield", revealed, game);

                game.addEffect(new GainAbilityTargetPerpetuallyEffect(
                        new SimpleStaticAbility(
                                Zone.ALL, new SpellCostIncreaseSourceEffect(1)
                        )
                ).setTargetPointer(new FixedTarget(card, game)), source);
            }
            return true;
        }
        return false;
    }
}
