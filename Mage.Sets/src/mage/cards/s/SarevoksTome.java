package mage.cards.s;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CompletedDungeonCondition;
import mage.abilities.condition.common.HaveInitiativeCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalManaEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SarevoksTome extends CardImpl {

    public SarevoksTome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // When Sarevok's Tome enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));

        // {T}: Add {C}. If you have the initiative, add {C}{C} instead.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD,
                new ConditionalManaEffect(
                        new BasicManaEffect(Mana.ColorlessMana(2)),
                        new BasicManaEffect(Mana.ColorlessMana(1)),
                        HaveInitiativeCondition.instance, "Add {C}. " +
                        "If you have the initiative, add {C}{C} instead."
                ), new TapSourceCost()
        ));

        // {3}, {T}: Exile cards from the top of your library until you exile a nonland card. You may cast that card without paying its mana cost. Activate only if you've completed a dungeon.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new SarevoksTomeEffect(),
                new GenericManaCost(3), CompletedDungeonCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(CompletedDungeonCondition.getHint()), new CompletedDungeonWatcher());
    }

    private SarevoksTome(final SarevoksTome card) {
        super(card);
    }

    @Override
    public SarevoksTome copy() {
        return new SarevoksTome(this);
    }
}

class SarevoksTomeEffect extends OneShotEffect {

    SarevoksTomeEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile a nonland card. " +
                "You may cast that card without paying its mana cost";
    }

    private SarevoksTomeEffect(final SarevoksTomeEffect effect) {
        super(effect);
    }

    @Override
    public SarevoksTomeEffect copy() {
        return new SarevoksTomeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Card card : player.getLibrary().getCards(game)) {
            player.moveCards(card, Zone.EXILED, source, game);
            if (!card.isLand(game)) {
                CardUtil.castSpellWithAttributesForFree(player, source, game, card);
                break;
            }
        }
        return true;
    }
}
