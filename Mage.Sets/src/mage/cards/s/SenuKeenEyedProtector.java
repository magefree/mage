package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceInExileCondition;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SenuKeenEyedProtector extends CardImpl {

    private static final FilterPermanent filter =
            new FilterControlledCreaturePermanent("a legendary creature you control");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public SenuKeenEyedProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}, Exile Senu, Keen-Eyed Protector: You gain 2 life and scry 2.
        Ability ability = new SimpleActivatedAbility(
                new GainLifeEffect(2),
                new TapSourceCost()
        );
        ability.addCost(new ExileSourceCost());
        ability.addEffect(new ScryEffect(2).concatBy("and"));
        this.addAbility(ability);

        // When a legendary creature you control attacks and isn't blocked, if Senu is exiled, put it onto the battlefield attacking.
        this.addAbility(
                new ConditionalInterveningIfTriggeredAbility(
                        new AttacksAndIsNotBlockedAllTriggeredAbility(
                                Zone.EXILED, new SenuKeenEyedProtectorEffect(), filter
                        ), SourceInExileCondition.instance,
                        "When a legendary creature you control attacks and isn't blocked, "
                                + "if {this} is exiled, put it onto the battlefield attacking"
                )
        );
    }

    private SenuKeenEyedProtector(final SenuKeenEyedProtector card) {
        super(card);
    }

    @Override
    public SenuKeenEyedProtector copy() {
        return new SenuKeenEyedProtector(this);
    }
}

class SenuKeenEyedProtectorEffect extends OneShotEffect {

    SenuKeenEyedProtectorEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "put it onto the battlefield attacking";
    }

    private SenuKeenEyedProtectorEffect(final SenuKeenEyedProtectorEffect effect) {
        super(effect);
    }

    @Override
    public SenuKeenEyedProtectorEffect copy() {
        return new SenuKeenEyedProtectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = source.getSourceCardIfItStillExists(game);
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent creature = game.getPermanent(card.getId());
        if (creature != null) {
            game.getCombat().addAttackingCreature(creature.getId(), game);
        }
        return true;
    }

}