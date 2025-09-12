package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttachmentAttachedToCardTypePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;
import mage.target.TargetImpl;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KitsuneMystic extends CardImpl {

    private static final Condition condition = new EnchantedSourceCondition(2);

    public KitsuneMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.flipCard = true;
        this.flipCardName = "Autumn-Tail, Kitsune Sage";

        // At the beginning of the end step, if Kitsune Mystic is enchanted by two or more Auras, flip it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new FlipSourceEffect(new AutumnTailKitsuneSage()).setText("flip it"), false, condition
        ));
    }

    private KitsuneMystic(final KitsuneMystic card) {
        super(card);
    }

    @Override
    public KitsuneMystic copy() {
        return new KitsuneMystic(this);
    }
}

class AutumnTailKitsuneSage extends TokenImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Aura attached to a creature");

    static {
        filter.add(new AttachmentAttachedToCardTypePredicate(CardType.CREATURE));
        filter.add(SubType.AURA.getPredicate());
    }

    AutumnTailKitsuneSage() {
        super("Autumn-Tail, Kitsune Sage", "");
        this.supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.FOX);
        subtype.add(SubType.WIZARD);
        power = new MageInt(4);
        toughness = new MageInt(5);

        // {1}: Attach target Aura attached to a creature to another creature.
        Ability ability = new SimpleActivatedAbility(new AutumnTailEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AutumnTailKitsuneSage(final AutumnTailKitsuneSage token) {
        super(token);
    }

    public AutumnTailKitsuneSage copy() {
        return new AutumnTailKitsuneSage(this);
    }
}

class AutumnTailEffect extends OneShotEffect {

    AutumnTailEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "attach target Aura attached to a creature to another creature";
    }

    private AutumnTailEffect(final AutumnTailEffect effect) {
        super(effect);
    }

    @Override
    public AutumnTailEffect copy() {
        return new AutumnTailEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent aura = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || aura == null) {
            return false;
        }
        FilterPermanent filter = new FilterCreaturePermanent();
        filter.add(Predicates.not(new PermanentIdPredicate(aura.getAttachedTo())));
        if (!game.getBattlefield().contains(filter, source.getControllerId(), source, game, 1)) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        return Optional
                .ofNullable(target)
                .map(TargetImpl::getFirstTarget)
                .map(game::getPermanent)
                .filter(permanent -> permanent.addAttachment(aura.getId(), source, game))
                .isPresent();
    }
}
