package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author muz
 */
public final class ArmorWars extends CardImpl {

    private static final FilterArtifactCard filterArtifactCard = new FilterArtifactCard("artifact spells");

    public ArmorWars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- You may draw a card for each artifact you control. If you do, each opponent draws a card.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new ArmorWarsEffect());

        // II -- Artifact spells you cast this turn cost {1} less to cast.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II,
            new SpellsCostReductionControllerEffect(filterArtifactCard, 1).setDuration(Duration.EndOfTurn)
                .setText("artifact spells you cast this turn cost {1} less to cast"));

        // III -- This Saga deals X damage to target opponent, where X is the greatest mana value among artifacts you control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III,
            new DamageTargetEffect(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_ARTIFACTS)
                .setText("this Saga deals X damage to target opponent, where X is the greatest mana value among artifacts you control"),
            new TargetOpponent());

        this.addAbility(sagaAbility);
    }

    private ArmorWars(final ArmorWars card) {
        super(card);
    }

    @Override
    public ArmorWars copy() {
        return new ArmorWars(this);
    }
}

class ArmorWarsEffect extends OneShotEffect {

    ArmorWarsEffect() {
        super(Outcome.DrawCard);
        staticText = "you may draw a card for each artifact you control. If you do, each opponent draws a card";
    }

    private ArmorWarsEffect(final ArmorWarsEffect effect) {
        super(effect);
    }

    @Override
    public ArmorWarsEffect copy() {
        return new ArmorWarsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int artifacts = ArtifactYouControlCount.instance.calculate(game, source, this);
        if (artifacts > 0
                && controller.chooseUse(outcome, "Draw a card for each artifact you control? (each opponent also draws a card)", source, game)) {
            controller.drawCards(artifacts, source, game);
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    opponent.drawCards(1, source, game);
                }
            }
        }
        return true;
    }
}
