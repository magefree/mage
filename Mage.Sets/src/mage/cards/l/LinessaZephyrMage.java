package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.GrandeurAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetadjustment.XCMCPermanentAdjuster;

/**
 *
 * @author emerald000
 */
public final class LinessaZephyrMage extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature with mana value X");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public LinessaZephyrMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {X}{U}{U}, {tap}: Return target creature with converted mana cost X to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl("{X}{U}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(XCMCPermanentAdjuster.instance);
        this.addAbility(ability);

        // Grandeur - Discard another card named Linessa, Zephyr Mage: Target player returns a creature they control to its owner's hand, then repeats this process for an artifact, an enchantment, and a land.
        ability = new GrandeurAbility(new LinessaZephyrMageEffect(), "Linessa, Zephyr Mage");
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private LinessaZephyrMage(final LinessaZephyrMage card) {
        super(card);
    }

    @Override
    public LinessaZephyrMage copy() {
        return new LinessaZephyrMage(this);
    }
}

class LinessaZephyrMageEffect extends OneShotEffect {

    LinessaZephyrMageEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Target player returns a creature they control to its owner's hand, then repeats this process for an artifact, an enchantment, and a land";
    }

    LinessaZephyrMageEffect(final LinessaZephyrMageEffect effect) {
        super(effect);
    }

    @Override
    public LinessaZephyrMageEffect copy() {
        return new LinessaZephyrMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player targetPlayer = game.getPlayer(source.getFirstTarget());
            if (targetPlayer != null) {
                // Target player returns a creature they control to its owner's hand,
                Target target = new TargetControlledCreaturePermanent();
                target.setNotTarget(true);
                if (target.choose(Outcome.ReturnToHand, targetPlayer.getId(), source.getSourceId(), source, game)) {
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        targetPlayer.moveCards(permanent, Zone.HAND, source, game);
                    }
                }

                // then repeats this process for an artifact,
                FilterControlledPermanent filter = new FilterControlledPermanent("artifact you control");
                filter.add(CardType.ARTIFACT.getPredicate());
                target = new TargetControlledPermanent(filter);
                target.setNotTarget(true);
                if (target.choose(Outcome.ReturnToHand, targetPlayer.getId(), source.getSourceId(), source, game)) {
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        targetPlayer.moveCards(permanent, Zone.HAND, source, game);
                    }
                }

                // an enchantment,
                filter = new FilterControlledPermanent("enchantment you control");
                filter.add(CardType.ENCHANTMENT.getPredicate());
                target = new TargetControlledPermanent(filter);
                target.setNotTarget(true);
                if (target.choose(Outcome.ReturnToHand, targetPlayer.getId(), source.getSourceId(), source, game)) {
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        targetPlayer.moveCards(permanent, Zone.HAND, source, game);
                    }
                }

                // and a land.
                filter = new FilterControlledPermanent("land you control");
                filter.add(CardType.LAND.getPredicate());
                target = new TargetControlledPermanent(filter);
                target.setNotTarget(true);
                if (target.choose(Outcome.ReturnToHand, targetPlayer.getId(), source.getSourceId(), source, game)) {
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        targetPlayer.moveCards(permanent, Zone.HAND, source, game);
                    }
                }

                return true;
            }
        }
        return false;
    }
}
