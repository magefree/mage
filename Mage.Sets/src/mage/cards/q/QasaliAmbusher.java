
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class QasaliAmbusher extends CardImpl {

    public QasaliAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // If a creature is attacking you and you control a Forest and a Plains, you may casbt Qasali Ambusher without paying its mana cost and as though it had flash.
        this.addAbility(new QasaliAmbusherAbility());

    }

    public QasaliAmbusher(final QasaliAmbusher card) {
        super(card);
    }

    @Override
    public QasaliAmbusher copy() {
        return new QasaliAmbusher(this);
    }
}

class QasaliAmbusherAbility extends ActivatedAbilityImpl {

    private static final FilterControlledLandPermanent filterPlains = new FilterControlledLandPermanent();
    private static final FilterControlledLandPermanent filterForest = new FilterControlledLandPermanent();

    static {
        filterPlains.add(new SubtypePredicate(SubType.PLAINS));
        filterForest.add(new SubtypePredicate(SubType.FOREST));
    }

    public QasaliAmbusherAbility() {
        super(Zone.HAND, new QasaliAmbusherEffect(), new ManaCostsImpl());
        this.timing = TimingRule.INSTANT;
        this.usesStack = false;
    }

    public QasaliAmbusherAbility(final QasaliAmbusherAbility ability) {
        super(ability);
    }

    @Override
    public QasaliAmbusherAbility copy() {
        return new QasaliAmbusherAbility(this);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (!game.getBattlefield().getActivePermanents(filterPlains, this.getControllerId(), this.getSourceId(), game).isEmpty()
                && !game.getBattlefield().getActivePermanents(filterForest, this.getControllerId(), this.getSourceId(), game).isEmpty()) {
            for (CombatGroup group : game.getCombat().getGroups()) {
                if (isControlledBy(group.getDefenderId())) {
                    return super.canActivate(playerId, game);
                }
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    @Override
    public String getRule() {
        return "If a creature is attacking you and you control a Forest and a Plains, you may cast {this} without paying its mana cost and as though it had flash.";
    }
}

class QasaliAmbusherEffect extends OneShotEffect {

    public QasaliAmbusherEffect() {
        super(Outcome.Benefit);
        staticText = "";
    }

    public QasaliAmbusherEffect(final QasaliAmbusherEffect effect) {
        super(effect);
    }

    @Override
    public QasaliAmbusherEffect copy() {
        return new QasaliAmbusherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = (Card) game.getObject(source.getSourceId());
        if (card != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                SpellAbility spellAbility = card.getSpellAbility();
                spellAbility.clear();
                return controller.cast(spellAbility, game, true, new MageObjectReference(source.getSourceObject(game), game));
            }
        }
        return false;
    }
}
