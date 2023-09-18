package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DireFleetDaredevil extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public DireFleetDaredevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When Dire Fleet Daredevil enters the battlefield, exile target instant or sorcery card from an opponent's graveyard. 
        // You may cast it this turn, and you may spend mana as though it were mana of any type to cast that spell. 
        // If that spell would be put into a graveyard this turn, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DireFleetDaredevilEffect());
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter));
        this.addAbility(ability);
    }

    private DireFleetDaredevil(final DireFleetDaredevil card) {
        super(card);
    }

    @Override
    public DireFleetDaredevil copy() {
        return new DireFleetDaredevil(this);
    }
}

class DireFleetDaredevilEffect extends OneShotEffect {

    DireFleetDaredevilEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target instant or sorcery card from an opponent's graveyard. " +
                "You may cast it this turn, and mana of any type can be spent to cast that spell. "
                + ThatSpellGraveyardExileReplacementEffect.RULE_A;
    }

    private DireFleetDaredevilEffect(final DireFleetDaredevilEffect effect) {
        super(effect);
    }

    @Override
    public DireFleetDaredevilEffect copy() {
        return new DireFleetDaredevilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card targetCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || targetCard == null) {
            return false;
        }
        if (controller.moveCards(targetCard, Zone.EXILED, source, game)) {
            Card card = game.getCard(targetCard.getId());
            if (card != null) {
                // you may play and spend any mana
                CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, true);
                // exile from graveyard
                ContinuousEffect effect = new ThatSpellGraveyardExileReplacementEffect(false);
                effect.setTargetPointer(new FixedTarget(card, game));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}
