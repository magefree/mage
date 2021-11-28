package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class LimDulTheNecromancer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature an opponent controls");
    private static final FilterPermanent filter2 = new FilterPermanent("Zombie");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter2.add(SubType.ZOMBIE.getPredicate());
    }

    public LimDulTheNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a creature an opponent controls dies, you may pay {1}{B}. If you do, return that card to the battlefield under your control. If it's a creature, it's a Zombie in addition to its other creature types.
        this.addAbility(new DiesCreatureTriggeredAbility(new DoIfCostPaid(new LimDulTheNecromancerEffect(), new ManaCostsImpl("{1}{B}")), false, filter, true));

        // {1}{B}: Regenerate target Zombie.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateTargetEffect(), new ManaCostsImpl("{1}{B}"));
        ability2.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability2);

    }

    private LimDulTheNecromancer(final LimDulTheNecromancer card) {
        super(card);
    }

    @Override
    public LimDulTheNecromancer copy() {
        return new LimDulTheNecromancer(this);
    }
}

class LimDulTheNecromancerEffect extends OneShotEffect {

    public LimDulTheNecromancerEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return that card to the battlefield under your control. If it's a creature, it's a Zombie in addition to its other creature types";
    }

    public LimDulTheNecromancerEffect(final LimDulTheNecromancerEffect effect) {
        super(effect);
    }

    @Override
    public LimDulTheNecromancerEffect copy() {
        return new LimDulTheNecromancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(targetPointer.getFirst(game, source));
            if (card != null) {
                if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)
                        && card.isCreature(game)) {
                    Permanent creature = game.getPermanent(card.getId());
                    ContinuousEffect effect = new AddCardSubTypeTargetEffect(SubType.ZOMBIE, Duration.WhileOnBattlefield);
                    effect.setTargetPointer(new FixedTarget(creature.getId(), game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
