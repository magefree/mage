package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.replacement.LeaveBattlefieldExileTargetReplacementEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2 & L_J
 */
public final class DreamsOfTheDead extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("white or black creature card");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLACK)));
    }

    public DreamsOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // {1}{U}: Return target white or black creature card from your graveyard to the battlefield. That creature gains "Cumulative upkeep {2}." If the creature would leave the battlefield, exile it instead of putting it anywhere else.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DreamsOfTheDeadEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        ability.addEffect(new LeaveBattlefieldExileTargetReplacementEffect("the creature"));
        this.addAbility(ability);
    }

    private DreamsOfTheDead(final DreamsOfTheDead card) {
        super(card);
    }

    @Override
    public DreamsOfTheDead copy() {
        return new DreamsOfTheDead(this);
    }
}

class DreamsOfTheDeadEffect extends OneShotEffect {

    DreamsOfTheDeadEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return target white or black creature card from your graveyard to the battlefield. That creature gains \"Cumulative upkeep {2}.\"";
    }

    private DreamsOfTheDeadEffect(final DreamsOfTheDeadEffect effect) {
        super(effect);
    }

    @Override
    public DreamsOfTheDeadEffect copy() {
        return new DreamsOfTheDeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null) {
            if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                Permanent creature = game.getPermanent(card.getId());
                if (creature != null) {
                    ContinuousEffect effect = new GainAbilityTargetEffect(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{2}")), Duration.Custom);
                    effect.setTargetPointer(new FixedTarget(creature, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
