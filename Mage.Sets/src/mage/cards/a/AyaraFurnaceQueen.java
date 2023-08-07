package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AyaraFurnaceQueen extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact or creature card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public AyaraFurnaceQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);
        this.color.setRed(true);
        this.nightCard = true;

        // At the beginning of combat on your turn, return up to one target artifact or creature card from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new AyaraFurnaceQueenEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.addAbility(ability);
    }

    private AyaraFurnaceQueen(final AyaraFurnaceQueen card) {
        super(card);
    }

    @Override
    public AyaraFurnaceQueen copy() {
        return new AyaraFurnaceQueen(this);
    }
}

class AyaraFurnaceQueenEffect extends OneShotEffect {

    AyaraFurnaceQueenEffect() {
        super(Outcome.Benefit);
        staticText = "return up to one target artifact or creature card from your graveyard " +
                "to the battlefield. It gains haste. Exile it at the beginning of the next end step";
    }

    private AyaraFurnaceQueenEffect(final AyaraFurnaceQueenEffect effect) {
        super(effect);
    }

    @Override
    public AyaraFurnaceQueenEffect copy() {
        return new AyaraFurnaceQueenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        if (game.getPermanent(card.getId()) == null) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.Custom
        ).setTargetPointer(new FixedTarget(card.getId(), game)), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ExileTargetEffect().setText("exile it")
                        .setTargetPointer(new FixedTarget(card.getId(), game)),
                TargetController.ANY
        ), source);
    return true;}
}
